
package mage.cards.t;

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
 * @author jeffwadsworth
 */
public final class ThirstForKnowledge extends CardImpl {

    public ThirstForKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");


        // Draw three cards. Then discard two cards unless you discard an artifact card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new ThirstforKnowledgeEffect());
    }

    public ThirstForKnowledge(final ThirstForKnowledge card) {
        super(card);
    }

    @Override
    public ThirstForKnowledge copy() {
        return new ThirstForKnowledge(this);
    }
}

class ThirstforKnowledgeEffect extends OneShotEffect {

    public ThirstforKnowledgeEffect() {
        super(Outcome.Damage);
        staticText = "Then discard two cards unless you discard an artifact card";
    }

    public ThirstforKnowledgeEffect(final ThirstforKnowledgeEffect effect) {
        super(effect);
    }

    @Override
    public ThirstforKnowledgeEffect copy() {
        return new ThirstforKnowledgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        FilterCard filter = new FilterCard("artifact to discard");
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        if (you != null
                && you.getHand().count(filter, game) > 0
                && you.chooseUse(Outcome.Discard, "Do you want to discard an artifact?  If you don't, you must discard 2 cards", source, game)) {
            Cost cost = new DiscardTargetCost(new TargetCardInHand(filter));
            if (cost.canPay(source, source.getSourceId(), you.getId(), game)) {
                if (cost.pay(source, game, source.getSourceId(), you.getId(), false, null)) {
                    return true;
                }
            }
        }
        if (you != null) {
            you.discard(2, false, source, game);
            return true;
        }
        return false;
    }
}