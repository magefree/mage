package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author L_J
 */
public final class LastRites extends CardImpl {

    public LastRites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Discard any number of cards. Target player reveals their hand, then 
        // you choose a nonland card from it for each card discarded
        // this way. That player discards those cards.
        this.getSpellAbility().addEffect(new LastRitesEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private LastRites(final LastRites card) {
        super(card);
    }

    @Override
    public LastRites copy() {
        return new LastRites(this);
    }
}

class LastRitesEffect extends OneShotEffect {

    LastRitesEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "Discard any number of cards. Target player reveals "
                + "their hand, then you choose a nonland card from it for "
                + "each card discarded this way. That player discards those cards";
    }

    private LastRitesEffect(final LastRitesEffect effect) {
        super(effect);
    }

    @Override
    public LastRitesEffect copy() {
        return new LastRitesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller == null || targetPlayer == null) {
            return false;
        }
        int discardCount = controller.discard(0, Integer.MAX_VALUE, false, source, game).size();
        FilterCard filter = new FilterCard((discardCount > 1 ? "" : "a")
                + " nonland card" + (discardCount > 1 ? "s" : ""));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        Effect effect = new DiscardCardYouChooseTargetEffect(StaticValue.get(discardCount), filter);
        effect.setTargetPointer(new FixedTarget(targetPlayer.getId()));
        effect.apply(game, source);
        return true;
    }
}
