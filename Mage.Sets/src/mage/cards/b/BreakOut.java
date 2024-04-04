package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author DominionSpy
 */
public final class BreakOut extends CardImpl {

    public BreakOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{G}");

        // Look at the top six cards of your library. You may reveal a creature card from among them.
        // If that card has mana value 2 or less, you may put it onto the battlefield and it gains haste until end of turn.
        // If you didn't put the revealed card onto the battlefield this way, put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new BreakOutEffect());
    }

    private BreakOut(final BreakOut card) {
        super(card);
    }

    @Override
    public BreakOut copy() {
        return new BreakOut(this);
    }
}

class BreakOutEffect extends OneShotEffect {

    BreakOutEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top six cards of your library. You may reveal a creature card from among them. " +
                "If that card has mana value 2 or less, you may put it onto the battlefield and it gains haste until end of turn. " +
                "If you didn't put the revealed card onto the battlefield this way, put it into your hand. " +
                "Put the rest on the bottom of your library in a random order.";
    }

    private BreakOutEffect(final BreakOutEffect effect) {
        super(effect);
    }

    @Override
    public BreakOutEffect copy() {
        return new BreakOutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 6));
        controller.lookAtCards(source, null, cards, game);

        TargetCard target = new TargetCard(0, 1, Zone.LIBRARY, StaticFilters.FILTER_CARD_CREATURE);
        target.withChooseHint("Reveal a creature card?");
        if (!controller.chooseTarget(outcome, cards, target, source, game)) {
            return PutCards.BOTTOM_RANDOM.moveCards(controller, cards, source, game);
        }

        Card pickedCard = game.getCard(target.getFirstTarget());
        if (pickedCard != null) {
            controller.revealCards(source, new CardsImpl(pickedCard), game);
            cards.remove(pickedCard);

            if (pickedCard.getManaValue() <= 2 &&
                    controller.chooseUse(Outcome.PutCardInPlay, "Put it onto the battlefield?", source, game) &&
                    controller.moveCards(pickedCard, Zone.BATTLEFIELD, source, game)) {
                Permanent permanent = game.getPermanent(pickedCard.getId());
                if (permanent != null) {
                    ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
            } else {
                controller.moveCards(pickedCard, Zone.HAND, source, game);
            }
        }

        PutCards.BOTTOM_RANDOM.moveCards(controller, cards, source, game);

        return true;
    }
}
