
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class MysticMeditation extends CardImpl {

    public MysticMeditation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");

        // Draw three cards. Then discard two cards unless you discard a creature card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new MysticMeditationEffect());

    }

    public MysticMeditation(final MysticMeditation card) {
        super(card);
    }

    @Override
    public MysticMeditation copy() {
        return new MysticMeditation(this);
    }
}

class MysticMeditationEffect extends OneShotEffect {

    public MysticMeditationEffect() {
        super(Outcome.Damage);
        staticText = "Then discard two cards unless you discard a creature card";
    }

    public MysticMeditationEffect(final MysticMeditationEffect effect) {
        super(effect);
    }

    @Override
    public MysticMeditationEffect copy() {
        return new MysticMeditationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        FilterCard filter = new FilterCard("creature card to discard");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        if (controller != null
                && controller.getHand().count(filter, game) > 0
                && controller.chooseUse(Outcome.Discard, "Do you want to discard a creature card?  If you don't, you must discard 2 cards", source, game)) {
            Cost cost = new DiscardTargetCost(new TargetCardInHand(filter));
            if (cost.canPay(source, source.getSourceId(), controller.getId(), game)) {
                if (cost.pay(source, game, source.getSourceId(), controller.getId(), false, null)) {
                    return true;
                }
            }
        }
        if (controller != null) {
            controller.discard(2, false, source, game);
            return true;
        }
        return false;
    }
}