package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UltimeciaTemporalThreat extends CardImpl {

    public UltimeciaTemporalThreat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Ultimecia enters, tap all creatures your opponents control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new TapAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES)
        ));

        // Whenever a creature you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.NONE, true
        ));
    }

    private UltimeciaTemporalThreat(final UltimeciaTemporalThreat card) {
        super(card);
    }

    @Override
    public UltimeciaTemporalThreat copy() {
        return new UltimeciaTemporalThreat(this);
    }
}
