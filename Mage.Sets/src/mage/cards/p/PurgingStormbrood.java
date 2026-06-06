package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.RemoveAllCountersPermanentTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.OmenCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class PurgingStormbrood extends OmenCard {

    public PurgingStormbrood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{4}{B}",
                "Absorb Essence",
                new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Purging Stormbrood
        this.getLeftHalfCard().setPT(4, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Ward--Pay 2 life.
        this.getLeftHalfCard().addAbility(new WardAbility(new PayLifeCost(2), false));

        // When this creature enters, remove all counters from up to one target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new RemoveAllCountersPermanentTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.getLeftHalfCard().addAbility(ability);

        // Absorb Essence
        // Target creature gets +2/+2 and gains lifelink and hexproof until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 2)
                .setText("Target creature gets +2/+2"));
        this.getRightHalfCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                .setText("gains lifelink")
                .concatBy("and"));
        this.getRightHalfCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                .setText("hexproof until end of turn")
                .concatBy("and"));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        finalizeCard();
    }

    private PurgingStormbrood(final PurgingStormbrood card) {
        super(card);
    }

    @Override
    public PurgingStormbrood copy() {
        return new PurgingStormbrood(this);
    }
}
