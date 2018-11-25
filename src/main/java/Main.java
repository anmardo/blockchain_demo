
import model.Block;
import model.BlockChain;
import model.Transaction;

import java.util.ArrayList;
import java.util.List;

//@Slf4j
public class Main {


    public static void main(String[] args) {


        BlockChain myBC = new BlockChain(3);
        List<Transaction> txns  =new ArrayList<>();
        txns.add(new Transaction("Harry", 1000d, "Hermione"));
        myBC.addBlock(new Block("first", txns));

        List<Transaction> txns1  =new ArrayList<>();
        txns1.add(new Transaction("Hermione", 500d, "Ron"));
        txns1.add(new Transaction("Hermione", 200d, "Ginny"));
        myBC.addBlock(new Block("second", txns1));

        System.out.println(myBC);
        System.out.println("Is this valid? " + myBC.isBlockChainValid());




    }//main

}