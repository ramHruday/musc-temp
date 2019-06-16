package cn.edu.nju.mutestdemo.Controller;

import cn.edu.nju.mutestdemo.FileUtils.CopyDir;
import cn.edu.nju.mutestdemo.Model.MutationTestResult;
import cn.edu.nju.mutestdemo.MutationTestUtil.MutationTestStater;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;

@RequestMapping
@Controller
public class MutationTestController {
    @RequestMapping("/generateMutationTest")
    @ResponseBody
    public String generateMutationTest(@RequestParam("chainCode")String chainCode,@RequestParam("projectPath")String projectPath,@RequestParam("mutants")String mutantsJSON) throws IOException {
        Process proc=null;
        if(chainCode!=null&&chainCode!=""){
            /*String cmd="cmd /c pushd "+projectPath+"\\MuSC_dup"+" && "+chainCode+" > "+"C:\\Users\\belikout\\Desktop\\read.txt";
            try {
                proc = Runtime.getRuntime().exec(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            generateStartChainFile(projectPath,chainCode);
            String cmd = "cmd /k start "+projectPath+"\\MuSC_dup\\MuSC_StartTestChain.bat";
            //String cmd = "cmd.exe /C start /b " +projectPath+"\\MuSC_dup\\MuSC_StartTestChain.bat";
            try {
                Process ps = Runtime.getRuntime().exec(cmd);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            /*File filePath = new File(projectPath + "\\MuSC_dup\\MuSC_StartTestChain.bat");
            proc = Runtime.getRuntime().exec(filePath.toString());
            InputStreamReader inputStreamReader = new InputStreamReader(proc.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            try {
                while((line = bufferedReader.readLine()) !=null ) {
                    System.out.println("attention!!!:  "+line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        ArrayList<MutationTestResult> res=MutationTestStater.start(projectPath,mutantsJSON);
        //if(proc!=null)
            //proc.destroy();
        return JSON.toJSONString(res);
    }
    public void generateStartChainFile(String projectPath,String chainCode){
        File file = new File(projectPath + "\\MuSC_dup\\MuSC_StartTestChain.bat");
        String content="";
        content+="cd /d %~dp0\r\n";
        content+=chainCode+" > "+"C:\\Users\\belikout\\Desktop\\read.txt";
        FileWriter writer= null;
        try {
            writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/getProgress")
    @ResponseBody
    public int getProgress(@RequestParam("projectPath")String projectPath) throws IOException {
        File logFileDir=new File(projectPath+"\\MuSC_dup\\MuSC_MutationTestLog");
        if(logFileDir.exists()){//如果文件夹存在
            File[] files = logFileDir.listFiles();
            System.out.println(files.length);
            return files.length;
        }
        return 0;
    }
}
