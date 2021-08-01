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
public class CriticBlock extends AbstractBlock{
    BaseBlock base;
    public CriticBlock(){
        super((byte) 2);
        base=addChildBlock("base", new BaseBlock());
    }
    NDArray maskedMean(NDArray in,NDArray mask){
        NDArray zeroed=in.mul(mask);
        NDArray summed=zeroed.sum(new int[]{1});
        NDArray maskDiv=mask.sum(new int[]{1});
        return summed.div(maskDiv);
    }
    public NDList forwardInternal(ParameterStore parameterStore, NDList inputs, boolean training, PairList<String, Object> pairList) {
        NDArray preMask=base.forward(parameterStore, inputs, training, pairList).singletonOrThrow();
        NDArray actionMask=inputs.get(1);
        NDArray res=maskedMean(preMask, actionMask);
        return new NDList(res);
    }
    public Shape[] getOutputShapes(Shape[] inputs) {
        Shape[] res=new Shape[1];
        res[0]=new Shape(inputs[0].get(0),inputs[0].get(1));
        return res;
    }

    @Override
    protected void initializeChildBlocks(NDManager manager,DataType dataType, Shape... inputShapes){
        base.initialize(manager, dataType, inputShapes);
    }
}
