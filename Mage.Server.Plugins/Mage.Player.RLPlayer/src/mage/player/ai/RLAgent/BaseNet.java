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

public class BaseNet {
    Model net;
    Trainer train;
    Predictor<NDList,NDList> pred;
    boolean synced=false;
    BaseNet(Loss loss){
        net=makeModel();
        train=makeTrainer(net,loss);
        syncPredictor();
    }
    void train(NDManager nd,NDList inputs,NDList labels){
        synced=false;
        Batchifier batchmaker=new StackBatchifier();
        Batch batch=new Batch(nd, inputs, labels, 1, batchmaker, batchmaker, 0, 0);
        EasyTrain.trainBatch(train, batch);
        train.step();
        batch.close();
    }
    void syncPredictor(){
        pred=net.newPredictor(new NoopTranslator());
        synced=true;
    }
    NDArray predict(NDList input){
        if(!synced){
            syncPredictor();
        }
        NDArray res=null;
        try{
            res=pred.predict(input).singletonOrThrow();
        }
        catch(TranslateException e){
            System.out.println("Translate exception");
            System.out.println(e.getMessage());
            for (StackTraceElement s : e.getStackTrace()) {
                System.out.println(s);
              }
        }
        return res; 
    } 
    Trainer makeTrainer(Model model,Loss loss){
        Tracker lrt = Tracker.fixed(0.003f);
        Optimizer adam=Optimizer.adam().optLearningRateTracker(lrt).setRescaleGrad(1).build();
        DefaultTrainingConfig config = new DefaultTrainingConfig(loss)
        .optOptimizer(adam)
        .addTrainingListeners(TrainingListener.Defaults.logging());
        Trainer trainer = model.newTrainer(config);
        trainer.initialize(new Shape(HParams.batchSize, 0));
        Metrics metrics = new Metrics();
        trainer.setMetrics(metrics);
        return trainer;
    }
    Model makeModel(){
        Model model = Model.newInstance("critic-net");
        BaseBlock block=new BaseBlock();
        model.setBlock(block);
        return model;
    }

}