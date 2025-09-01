package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HemosymbicMite extends CardImpl {

    public HemosymbicMite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.MITE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever this creature becomes tapped, another target creature you control gets +X/+X until end of turn, where X is this creature's power.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new BoostTargetEffect(
                SourcePermanentPowerValue.NOT_NEGATIVE, SourcePermanentPowerValue.NOT_NEGATIVE
        ));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private HemosymbicMite(final HemosymbicMite card) {
        super(card);
    }

    @Override
    public HemosymbicMite copy() {
        return new HemosymbicMite(this);
    }
}
