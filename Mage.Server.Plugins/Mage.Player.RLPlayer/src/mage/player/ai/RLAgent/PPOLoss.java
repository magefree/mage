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
import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.metric.Metrics;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.Block;
import ai.djl.nn.ParameterList;
import ai.djl.nn.SequentialBlock;
import ai.djl.nn.core.Linear;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.EasyTrain;
import ai.djl.training.Trainer;
import ai.djl.training.dataset.ArrayDataset;
import ai.djl.training.dataset.Batch;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import ai.djl.training.optimizer.Optimizer;
import ai.djl.training.tracker.Tracker;
import ai.djl.translate.NoopTranslator;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;

import java.io.IOException;
import java.nio.file.*;

public class PPOLoss extends Loss{
    PPOLoss(){
        super("PPOLoss");
    }

    @Override
    public NDArray evaluate(NDList labels, NDList predictions) {
        NDArray actTaken=labels.get(0);
        NDArray predReward=labels.get(1);
        NDArray reward=labels.get(2);
        NDArray baseLogProb=labels.get(3);
        NDArray logProbs=predictions.singletonOrThrow();

        NDArray advantage1D=reward.sub(predReward);
        advantage1D=advantage1D.sub(advantage1D.mean()); //Normalize mean of advantage1D to 0
        advantage1D=advantage1D.div(std(advantage1D).add(1e-5)); //and standard deviation 1
        //TODO change line below when switching to PPO
        NDArray advantage=actTaken.mul(reward);
        //NDArray advantage=actTaken.mul(advantage1D);

        NDArray action_ratio=logProbs.sub(baseLogProb).exp();
        NDArray unclipped=action_ratio.mul(advantage);
        float eps=.1f;
        NDArray clipped=action_ratio.clip(1-eps, 1+eps).mul(advantage);
        NDArray loss=unclipped.minimum(clipped);
        loss=loss.sum(new int[]{1});
        return loss.mean().mul(-1);
    }
    NDArray std(NDArray centeredData){
        return centeredData.square().mean().sqrt();
    }
}
