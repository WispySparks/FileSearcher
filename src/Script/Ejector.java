package Script;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ejector {

    Ejector() {

    }

    public void ejectDrive() {
            //ProcessBuilder builder = new ProcessBuilder("powershell.exe", "$driveEject = New-Object -comObject Shell.Application; $driveEject.Namespace(17).ParseName(\"D:\").InvokeVerb(\"Eject\")");
        try {
            //Process proc = builder.start();
            ProcessBuilder builder2 = new ProcessBuilder("powershell.exe", "Get-EventLog -LogName System -after (Get-Date).AddHours(-1) | Where-Object {$_.EventID -eq 225} | Format-Table -Wrap");
            Process proc = builder2.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            // for (int i =0; i<7; i++) {
            //     System.out.println(reader.readLine());
            // }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}