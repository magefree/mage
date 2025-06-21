package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.EffectKeyValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RampagingWarMammoth extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("up to X target artifacts");

    public RampagingWarMammoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(9);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cycling {X}{2}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{X}{2}{R}")));

        // When you cycle Rampaging War Mammoth, destroy up to X target artifacts.
        Ability ability = new CycleTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(0,1, filter));
        ability.setTargetAdjuster(new TargetsCountAdjuster(new EffectKeyValue("cycleXValue")));
        this.addAbility(ability);
    }

    private RampagingWarMammoth(final RampagingWarMammoth card) {
        super(card);
    }

    @Override
    public RampagingWarMammoth copy() {
        return new RampagingWarMammoth(this);
    }
}
