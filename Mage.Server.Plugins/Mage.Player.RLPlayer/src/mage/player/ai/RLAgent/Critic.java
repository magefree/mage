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
import ai.djl.training.loss.L2Loss;
import ai.djl.training.loss.Loss;
import ai.djl.training.optimizer.Optimizer;
import ai.djl.training.tracker.Tracker;
import ai.djl.translate.Batchifier;
import ai.djl.translate.NoopTranslator;
import ai.djl.translate.StackBatchifier;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;

import java.io.IOException;
import java.nio.file.*;

public class Critic extends BaseNet{
    Model net;
    Trainer train;
    Predictor<NDList,NDList> pred;
    boolean synced=false;
    Critic(){
        super(new L2Loss());
    }

    Model makeModel(){
        Model model = Model.newInstance("critic-net");
        CriticBlock block=new CriticBlock();
        model.setBlock(block);
        return model;
    }

}
