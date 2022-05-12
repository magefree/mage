package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 *
 * @author Quercitron
 */
public final class Flash extends CardImpl {

    public Flash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // You may put a creature card from your hand onto the battlefield. If you do, sacrifice it unless you pay its mana cost reduced by up to {2}.
        this.getSpellAbility().addEffect(new FlashEffect());
    }

    private Flash(final Flash card) {
        super(card);
    }

    @Override
    public Flash copy() {
        return new Flash(this);
    }
}

class FlashEffect extends OneShotEffect {

    private static final String choiceText = "Put a creature card from your hand onto the battlefield?";

    public FlashEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "You may put a creature card from your hand onto the battlefield. If you do, sacrifice it unless you pay its mana cost reduced by up to {2}.";
    }

    public FlashEffect(final FlashEffect effect) {
        super(effect);
    }

    @Override
    public FlashEffect copy() {
        return new FlashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.chooseUse(Outcome.PutCreatureInPlay, choiceText, source, game)) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE);
        if (controller.choose(Outcome.PutCreatureInPlay, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                ManaCosts<ManaCost> reducedCost = ManaCosts.removeVariableManaCost(CardUtil.reduceCost(card.getManaCost(), 2));
                if (controller.chooseUse(Outcome.Benefit, String.valueOf("Pay " + reducedCost.getText()) + Character.toString('?'), source, game)) {
                    reducedCost.clearPaid();
                    if (reducedCost.pay(source, game, source, source.getControllerId(), false, null)) {
                        return true;
                    }
                }

                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    permanent.sacrifice(source, game);
                }

                return true;
            }
        }
        return false;
    }

}
