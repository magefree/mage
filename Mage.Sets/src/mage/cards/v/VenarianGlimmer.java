
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Styxo
 */
public final class VenarianGlimmer extends CardImpl {

    public VenarianGlimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Target player reveals their hand. You choose a nonland card with converted mana cost X or less from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new VenarianGlimmerEffect());
    }

    private VenarianGlimmer(final VenarianGlimmer card) {
        super(card);
    }

    @Override
    public VenarianGlimmer copy() {
        return new VenarianGlimmer(this);
    }
}

class VenarianGlimmerEffect extends OneShotEffect {

    public VenarianGlimmerEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals their hand. You choose a nonland card with mana value X or less from it. That player discards that card";
    }

    private VenarianGlimmerEffect(final VenarianGlimmerEffect effect) {
        super(effect);
    }

    @Override
    public VenarianGlimmerEffect copy() {
        return new VenarianGlimmerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            FilterCard filter = new FilterNonlandCard();
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));
            Effect effect = new DiscardCardYouChooseTargetEffect(filter);
            effect.setTargetPointer(targetPointer);
            effect.apply(game, source);
            return true;
        }
        return false;
    }
}
