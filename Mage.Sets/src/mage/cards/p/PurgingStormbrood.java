package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.RemoveAllCountersPermanentTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.OmenCard;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Jmlundeen
 */
public final class PurgingStormbrood extends OmenCard {

    public PurgingStormbrood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{4}{B}", "Absorb Essence", "{1}{W}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward--Pay 2 life.
        this.addAbility(new WardAbility(new PayLifeCost(2)));

        // When this creature enters, remove all counters from up to one target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new RemoveAllCountersPermanentTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Absorb Essence
        // Target creature gets +2/+2 and gains lifelink and hexproof until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 2)
                .setText("Target creature gets +2/+2"));
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                .setText("gains lifelink")
                .concatBy("and"));
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                .setText("hexproof until end of turn")
                .concatBy("and"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.finalizeOmen();
    }

    private PurgingStormbrood(final PurgingStormbrood card) {
        super(card);
    }

    @Override
    public PurgingStormbrood copy() {
        return new PurgingStormbrood(this);
    }
}
