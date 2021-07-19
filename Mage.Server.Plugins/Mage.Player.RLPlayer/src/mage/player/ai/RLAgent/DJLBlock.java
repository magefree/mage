package mage.player.ai.RLAgent;
import ai.djl.*;
import ai.djl.nn.*;
import ai.djl.nn.core.*;
import ai.djl.nn.transformer.IdEmbedding;
import ai.djl.ndarray.*;
import ai.djl.ndarray.types.*;
import ai.djl.ndarray.index.*;
import ai.djl.training.ParameterStore;
import ai.djl.util.PairList;

public class DJLBlock extends AbstractBlock{
    IdEmbedding actionEmbed;
    Linear lin1;
    public DJLBlock(){
        super((byte) 2);
        actionEmbed=new IdEmbedding.Builder().setDictionarySize(HParams.maxRepresents).setEmbeddingSize(HParams.embedDim).build();
        lin1=Linear.builder().optBias(true).setUnits(1).build(); //Just a linear network for now :(
    }

    public NDList forwardInternal(ParameterStore parameterStore, NDList inputs, boolean training, PairList<String, Object> pairList) {
        NDArray actions=inputs.get(0);
        NDArray actionMask=inputs.get(1);
        NDArray embeddedActions=actionEmbed.forward(parameterStore, new NDList(actions), training, pairList).singletonOrThrow();
        Shape actionShape=embeddedActions.getShape();
        Shape newEmbedShape=new Shape(actionShape.get(0),actionShape.get(1),actionShape.get(2)*actionShape.get(3));
        NDArray reshapedActions=embeddedActions.reshape(newEmbedShape);
        NDArray pastLin=lin1.forward(parameterStore, new NDList(reshapedActions), training,pairList).singletonOrThrow();
        Shape resultShape=new Shape(actionShape.get(0),actionShape.get(1));
        pastLin=pastLin.reshape(resultShape);
        long sub_mult=1000000;
        pastLin=pastLin.sub(actionMask.mul(-1).add(1).mul(sub_mult));
        return new NDList(pastLin);
    }
    public Shape[] getOutputShapes(Shape[] inputs) {
        Shape[] res=new Shape[1];
        res[0]=new Shape(inputs[0].get(0),inputs[0].get(1));
        return res;
    }
}
