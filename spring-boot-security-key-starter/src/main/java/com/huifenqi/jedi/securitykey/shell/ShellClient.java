package com.huifenqi.jedi.securitykey.shell;

import com.jcraft.jsch.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by t3tiger on 2017/6/26.
 * http://www.jcraft.com/jsch/examples/
 */
public class ShellClient {

    private JSch jSch;
    private Session session;
    private Channel sFtpChannel;
    private ChannelSftp sftp;

    private String host;
    private int port;
    private String userName;
    private String privateKey;

    public ShellClient(String host, int port, String userName, String privateKey) {
        jSch = new JSch();

        this.host = host;
        this.port = port;
        this.userName = userName;
        this.privateKey = privateKey;
    }

    /**
     * 登陆连接
     *
     * @throws Exception
     */
    public void connect() throws Exception {
        /**
         * 防止重复登陆
         */
        if (null != session && session.isConnected()) {
            return;
        }

        synchronized (ShellClient.class) {
            /**
             * 防止重复登陆
             */
            if (null != session && session.isConnected()) {
                return;
            }
            /**
             * 登陆server，建立连接
             */
            try {
                jSch.addIdentity("jedi", privateKey.getBytes("UTF-8"), null, null);
            } catch (JSchException e) {
                e.printStackTrace();
                throw new Exception("private key is null or empty");
            }

            if (null == session) {
                session = jSch.getSession(userName, host, port);
            }
            session.setConfig("StrictHostKeyChecking", "no");

            session.connect(5000);
        }

    }

    public byte[] execSftpDownload2ByteArray(String filePath, String host, int port, String userName, String privateKey) {
        return execSftpDownload2ByteArray(filePath, host, port, userName, privateKey, null);
    }

    public byte[] execSftpDownload2ByteArray(String filePath, String host, int port, String userName, String privateKey, String passphrase) {
        byte[] ret = new byte[0];
        try {
            if (null == session || !session.isConnected()) {
                connect();
            }

            if (sftp == null || !sftp.isConnected()) {
                sFtpChannel = session.openChannel("sftp");
                sFtpChannel.connect();// 建立SFTP通道的连接
                sftp = (ChannelSftp) sFtpChannel;
            }

            sftp.cd("/data/conf/jedi");

            InputStream is = null;
            try {
                is = sftp.get(filePath);
            } catch (SftpException e) {
                System.out.println("找不到文件：" + filePath);
            }
            if (null == is) {
                return ret;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff = new byte[512];
            int len = -1;
            while ((len = is.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            ret = baos.toByteArray();
            baos.close();
            is.close();
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 注销登录，并断开到SFTP服务器的网络连接
     */
    public synchronized void close() {
        if (sftp != null && sftp.isConnected()) {
            sftp.disconnect();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }
}
