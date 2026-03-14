package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionTactics extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ALLY, "Allies you control");

    public InvasionTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // When this enchantment enters, creatures you control get +2/+2 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new BoostControlledEffect(2, 2, Duration.EndOfTurn)
        ));

        // Whenever one or more Allies you control deal combat damage to a player, draw a card.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter
        ));
    }

    private InvasionTactics(final InvasionTactics card) {
        super(card);
    }

    @Override
    public InvasionTactics copy() {
        return new InvasionTactics(this);
    }
}
