package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BlockChain {

    private int difficulty;
    private List<Block> blocks;

    public BlockChain(int difficulty){
        this.difficulty=difficulty;
        this.blocks = new ArrayList<Block>();
        Block genesis = new Block("genesis",new ArrayList<Transaction>());
        genesis.mineBlock(this.difficulty);
        this.blocks.add(genesis);
    }

    @Override
    public String toString(){
        return this.blocks.stream().map(Block::toString).collect(Collectors.joining("\n"));
    }

    public Block getLast(){
        if(this.blocks != null && !this.blocks.isEmpty())
            return this.blocks.size()==1?this.blocks.get(0):this.blocks.get(blocks.size()-1);

        return null;
    }

    public void addBlock(Block block){
        if(block != null){
            block.setIndex(this.blocks.isEmpty()?0:this.blocks.size()+1);
            block.setPreviousBlockHash(this.getLast().getBlockHash());
        }
        if(isNewBlockValid(block)){
            block.mineBlock(this.difficulty);
            this.blocks.add(block);
        }
    }

    public boolean isBlockChainValid(){
        return isFirstBlockValid() && this.blocks.stream().map(b->isBlockValid(b)).reduce(true,(x,y)->x.equals(y));
    }

    private boolean isFirstBlockValid() {
        Block firstBlock = this.blocks.get(0);
        return firstBlock != null && firstBlock.getIndex() == 0 && Block.calculateHash(firstBlock).equals(firstBlock.getBlockHash()) && firstBlock.getPreviousBlockHash() == null;
    }//isFirstBlockValid

    private boolean isBlockValid(Block b){

        if(b.getIndex()==0){
            return isFirstBlockValid();
        }

        Block previousB = this.blocks.size()==1?this.blocks.get(0):this.blocks.get(b.getIndex()-1);
        if(b != null && previousB != null){
            return b.getPreviousBlockHash().equals(previousB.getBlockHash()) && b.getBlockHash().equals(Block.calculateHash(b));
        }

        return false;
    }//isBlockValid

    private boolean isNewBlockValid(Block block){
        return block.getIndex()>0 && block.getPreviousBlockHash().equals(this.getLast().getBlockHash());
    }


}
