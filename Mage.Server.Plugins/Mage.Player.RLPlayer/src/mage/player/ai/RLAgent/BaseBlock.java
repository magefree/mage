package mage.player.ai.RLAgent;
import ai.djl.*;
import ai.djl.nn.*;
import ai.djl.nn.core.*;
import ai.djl.nn.transformer.IdEmbedding;
import ai.djl.ndarray.*;
import ai.djl.ndarray.types.*;
import ai.djl.ndarray.index.*;
import ai.djl.training.ParameterStore;
import ai.djl.training.hyperparameter.param.HpBool;
import ai.djl.util.PairList;


//TODO turn this into a proper network, and take in all available inputs
//For now, this is just a linear layer on top of actions
public class BaseBlock extends AbstractBlock{
    IdEmbedding actionEmbed;
    Linear lin1;
    public BaseBlock(){
        super((byte) 2);
        actionEmbed=addChildBlock("actionEmbedding",new IdEmbedding.Builder().setDictionarySize(HParams.maxRepresents).setEmbeddingSize(HParams.embedDim).build());
        lin1=addChildBlock("linear1",Linear.builder().optBias(true).setUnits(1).build()); //Just a linear network for now :(
    }

    public NDList forwardInternal(ParameterStore parameterStore, NDList inputs, boolean training, PairList<String, Object> pairList) {
        NDArray actions=inputs.get(0);
        NDArray embeddedActions=actionEmbed.forward(parameterStore, new NDList(actions), training, pairList).singletonOrThrow();
        Shape actionShape=embeddedActions.getShape();
        Shape newEmbedShape=new Shape(actionShape.get(0),actionShape.get(1),actionShape.get(2)*actionShape.get(3));
        NDArray reshapedActions=embeddedActions.reshape(newEmbedShape);
        NDArray pastLin=lin1.forward(parameterStore, new NDList(reshapedActions), training,pairList).singletonOrThrow();
        Shape resultShape=new Shape(actionShape.get(0),actionShape.get(1));
        pastLin=pastLin.reshape(resultShape);
        return new NDList(pastLin);
    }
    public Shape[] getOutputShapes(Shape[] inputs) {
        Shape[] res=new Shape[1];
        res[0]=new Shape(inputs[0].get(0),inputs[0].get(1));
        return res;
    }

    @Override
    protected void initializeChildBlocks(NDManager manager,DataType dataType, Shape... inputShapes){
        actionEmbed.initialize(manager, dataType, inputShapes);
        lin1.initialize(manager, dataType, new Shape(0,HParams.embedDim*HParams.actionParts));
    }
}
