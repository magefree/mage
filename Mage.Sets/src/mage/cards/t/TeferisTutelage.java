package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeferisTutelage extends CardImpl {

    public TeferisTutelage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // When Teferi's Tutelage enters the battlefield, draw a card, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));

        // Whenever you draw a card, target opponent mills two cards.
        Ability ability = new DrawCardControllerTriggeredAbility(new MillCardsTargetEffect(2), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TeferisTutelage(final TeferisTutelage card) {
        super(card);
    }

    @Override
    public TeferisTutelage copy() {
        return new TeferisTutelage(this);
    }
}
