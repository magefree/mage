package mage.sets.kaladesh;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * Created by IGOUDT on 16-9-2016.
 */
public class RushOfVitality extends CardImpl {

    public RushOfVitality(final UUID ownerId){
        super(ownerId,99,"Rush of Vitality", Rarity.COMMON, new CardType[]{CardType.INSTANT},"{1B}");
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn));

    }

    public RushOfVitality(final RushOfVitality rushOfVitality){
        super(rushOfVitality);
    }

    @Override
    public RushOfVitality copy() {
        return new RushOfVitality(this);
    }
}
