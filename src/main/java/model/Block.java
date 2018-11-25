package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@ToString
public class Block {



    private String blockHash;
    private String name;
    private int index;
    private String previousBlockHash;
    private List<Transaction> transactions;
    private int nuance;
    private long timestamp;

    public Block(String name,List<Transaction> transactions){
        this.name=name;
        this.transactions=transactions;
        this.timestamp=(new Date()).getTime();
        this.nuance=0;
        this.blockHash=Block.calculateHash(this);
    }


    public static String calculateHash(Block block){
        MessageDigest digest = null;
        String myHash="";
        try {
            digest = MessageDigest.getInstance("SHA-256");

            String txns = (block.transactions != null && !block.transactions.isEmpty())
                    ?block.transactions.stream().map(Transaction::toString).collect(Collectors.joining(","))
                    :"";

            String input = block.previousBlockHash + txns + Long.toString(block.timestamp) + block.nuance;
            //Applies sha256 to our input,
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            myHash = hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return myHash;
    }

    public String getBlockHash() {
        return this.blockHash;
    }

    public static boolean isValid(Block block){
        return Block.calculateHash(block).compareTo(block.getBlockHash()) == 0;
    }

    public void mineBlock(int difficulty){
        this.nuance=0;
        long init = new Date().getTime();
        System.out.println("\tStarting mining with difficulty="+difficulty + " at " + new Date());

        //change this so that the hash is calculated using SHA-256, in order to meet the zeroes check!
        while(!this.getBlockHash().startsWith(zeroes(difficulty))){
            this.nuance++;
            this.blockHash=Block.calculateHash(this);
            System.out.println("\t\tMining: nuance["+this.nuance+"],hash["+this.blockHash+"] ");
        }
        System.out.println("\tMining finished after " + ((new Date().getTime())-init) + " ms");
    }


    private String zeroes(int offset){
        String zeroes = "";
        for(int i=0;i<offset;i++){
            zeroes += "0";
        }
        return zeroes;
    }

    @Override
    public String toString(){
        return "Block #" + this.getIndex()
                + "\n\tname='"+this.getName()+"',"
                + "\n\tpreviousHash='"+this.getPreviousBlockHash()+"',"
                + "\n\thash='"+this.getBlockHash()+"',"
                + "\n\ttxns=\n\t\t["+this.getTransactions().stream().map(Transaction::toString).collect(Collectors.joining("\n\t\t"))+ "]";

    }




    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getNuance() {
        return nuance;
    }

    public void setNuance(int nuance) {
        this.nuance = nuance;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
