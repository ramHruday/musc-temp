package cn.edu.nju.mutestdemo.Sol2AST;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class GenASTServiceClient {

    public static void main(String[] args) {
        genAST("C:\\\\Users\\\\ramah\\\\Desktop\\\\test.sol");
    }

    public static String genAST(String SolPath) {

        TTransport transport = null;
        try {
            transport = new TSocket("localhost", 9898, 30000);
            TProtocol protocol = new TBinaryProtocol(transport);
            GenAST.Client client = new GenAST.Client(protocol);

            transport.open();
            String result = client.genAST(getFile(SolPath));
            System.out.println(result);
            transport.close();
            return result;
        } catch (TTransportException e) {
            // e.printStackTrace();
            System.out.println("TTTrasnport error   : " + e.getLocalizedMessage());

            return "TTransportError";
        } catch (TException e) {
            // e.printStackTrace();
            System.out.println("TEexception error   : " + e.getLocalizedMessage());

            return "TError";
        }
    }

    private static String getFile(String path) {
        String out = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));// 读取sol
            String s = null;

            while ((s = br.readLine()) != null) {
                out += s + "\n";
            }
            br.close();
            return out;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "error";
        }
    }

}
