package mage;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.DependencyType;
import mage.constants.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by IGOUDT on 25-2-2017.
 */
public class ContinuousEffectImplTest {

    @Test
    public void isDependentTo(){
        BoostTargetEffect ghe = new BoostTargetEffect(0,0, Duration.Custom);
        ghe.setDependedToType(DependencyType.AuraAddingRemoving);
        Set<UUID> x = ghe.isDependentTo(new ArrayList<>());
        Assertions.assertEquals(0, x.size());
    }
}
