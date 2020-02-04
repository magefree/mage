package mage;

import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.DependencyType;
import mage.constants.Duration;

/**
 * Created by IGOUDT on 25-2-2017.
 */
public class ContinuousEffectImplTest {

    @Test
    public void isDependentTo(){
        BoostTargetEffect ghe = new BoostTargetEffect(0,0, Duration.Custom);
        ghe.setDependedToType(DependencyType.AuraAddingRemoving);
        Set<UUID> x = ghe.isDependentTo(new ArrayList<>());
        Assert.assertThat(x.size(), is(0));
    }
}
