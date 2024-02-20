
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author LEGION
 */
public class Imagedownloader extends javax.swing.JFrame {

    private ExecutorService executorService;
    private ReentrantLock lock;
    private boolean paused;
    private Imagedownloader imagedownloader;
    private String status;


    /**
     * Creates new form Imagedownloader
     */
    public Imagedownloader() {
        initComponents();
        executorService = Executors.newFixedThreadPool(5);
        lock = new ReentrantLock();
        paused = false;
        status="";

    }
    
     private void pauseDownloads() {
       paused = true;
    }

    private void resumeDownloads() {
        paused = false;
    }

    // Method to cancel the image downloads
    private void cancelDownloads() {
        
       // Interrupts all actively executing tasks in the executor service
        executorService.shutdownNow(); 
        // Reset the paused status to false
        paused = false;
    }

    private void startDownload() {
            // Get URLs from text fields

        String[] urls = {url1.getText().trim(), url2.getText().trim(), url3.getText().trim()};
        
    // Iterate through the URLs and initiate download tasks for non-empty URLs
        for (int i = 0; i < urls.length; i++) {
            String url = urls[i];
            if (!url.isEmpty()) {
            // Execute a new ImageDownloadTask for each non-empty URL
                executorService.execute(new ImageDownloadTask(url, "Folder_" + i));
            }
        }
    }

    private void log(String message) {
        System.out.println(message + "\n");
    }

    private class ImageDownloadTask implements Runnable {

        private String url;
        private String folderName;
        private JTextField []status = {url1status,url2status,url3status};

        public ImageDownloadTask(String url, String folderName) {
            this.url = url;
            this.folderName = folderName;
        }

        @Override
        public void run() {
        try {
            // Check if downloads are paused, sleep for 1 second before checking again
            while (paused) {
                Thread.sleep(1000); // Sleep for 1 second before checking again
            }
            // Acquire the lock to control access to shared resources

            lock.lock();

            try {
                // Connect to the URL and fetch the document
                Document document = Jsoup.connect(url).get();
                 // Extract image elements from the document
                Elements imgElements = document.select("img");
                
                // Create a folder for storing downloaded images if it doesn't exist
                File folder = new File(folderName);
                if (!folder.exists()) {
                    folder.mkdir();
                }
                // Iterate through image elements and download images

                for (Element imgElement : imgElements) {
                    try {
                        // Check if downloads are paused, sleep for 1 second before checking again
                        while (paused) {
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        System.out.println("download interrupt" + e);
                    }
                    // Check if the current thread is interrupted, log download cancellation if true
                    if (Thread.currentThread().isInterrupted()) {
                        log("Download cancelled");
                        return;
                    }
                    // Update status fields to indicate downloading
                    for (JTextField st : status) {
                        st.setText("downloading...");

                    }
                    String imgUrl = imgElement.attr("src");
                    String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
                    InputStream in = new URL(imgUrl).openStream();
                    OutputStream out = new BufferedOutputStream(new FileOutputStream(folderName + "/" + fileName));

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }

                    in.close();
                    out.close();

                    log("Downloaded: " + fileName + " from " + url);
                }
                 for (JTextField st : status) {
                st.setText("downloaded");
            }
            } catch (MalformedURLException g) {
                JOptionPane.showMessageDialog(rootPane, "Invalid URL: " + url, "Malformed URL", JOptionPane.ERROR_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(Imagedownloader.this,
                        "Error downloading images from " + url + ": " + e.getMessage(),
                        "Download Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(Imagedownloader.this,
                    "Invalid URL: " + url,
                    "Invalid URL", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Release the lock
            lock.unlock();
           
        }
    }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        downloadbtn = new javax.swing.JButton();
        url1 = new javax.swing.JTextField();
        url2 = new javax.swing.JTextField();
        url3 = new javax.swing.JTextField();
        pause = new javax.swing.JButton();
        resume = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        url1status = new javax.swing.JTextField();
        url2status = new javax.swing.JTextField();
        url3status = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("URL 1");

        jLabel2.setText("URL 2");

        jLabel3.setText("URL 3");

        downloadbtn.setText("Download");
        downloadbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadbtnActionPerformed(evt);
            }
        });

        url1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                url1ActionPerformed(evt);
            }
        });

        url2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                url2ActionPerformed(evt);
            }
        });

        url3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                url3ActionPerformed(evt);
            }
        });

        pause.setText("pause");
        pause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseActionPerformed(evt);
            }
        });

        resume.setText("resume");
        resume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resumeActionPerformed(evt);
            }
        });

        cancel.setText("cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        url1status.setText("url 1");

        url2status.setText("url 2");

        url3status.setText("url 3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(url1status, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(url2status, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(url3status, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(url1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(url2, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(url3, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(179, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addComponent(pause)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(resume)
                            .addComponent(downloadbtn))
                        .addGap(105, 105, 105)))
                .addComponent(cancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(url1status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(url2status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(url3status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(url1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(url2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(url3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(downloadbtn)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pause)
                    .addComponent(resume)
                    .addComponent(cancel))
                .addContainerGap(176, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void downloadbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadbtnActionPerformed
        // TODO add your handling code here:
        startDownload();
        System.out.println("iinvoked");


    }//GEN-LAST:event_downloadbtnActionPerformed

    private void url1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_url1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_url1ActionPerformed

    private void url2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_url2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_url2ActionPerformed

    private void url3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_url3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_url3ActionPerformed

    private void pauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseActionPerformed
        // TODO add your handling code here:
        pauseDownloads();
    }//GEN-LAST:event_pauseActionPerformed

    private void resumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resumeActionPerformed
        // TODO add your handling code here:
        resumeDownloads();
    }//GEN-LAST:event_resumeActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // TODO add your handling code here:
        cancelDownloads();
        this.dispose();
    }//GEN-LAST:event_cancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Imagedownloader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Imagedownloader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Imagedownloader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Imagedownloader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Imagedownloader().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JButton downloadbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton pause;
    private javax.swing.JButton resume;
    private javax.swing.JTextField url1;
    private javax.swing.JTextField url1status;
    private javax.swing.JTextField url2;
    private javax.swing.JTextField url2status;
    private javax.swing.JTextField url3;
    private javax.swing.JTextField url3status;
    // End of variables declaration//GEN-END:variables
}
