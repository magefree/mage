
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LoneFox
 */
public final class Addle extends CardImpl {

    public Addle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose a color. Target player reveals their hand and you choose a card of that color from it. That player discards that card.
        this.getSpellAbility().addEffect(new AddleEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Addle(final Addle card) {
        super(card);
    }

    @Override
    public Addle copy() {
        return new Addle(this);
    }
}

class AddleEffect extends OneShotEffect {

    AddleEffect() {
        super(Outcome.Discard);
        staticText = "Choose a color. Target player reveals their hand and you choose a card of that color from it. That player discards that card.";
    }

    private AddleEffect(final AddleEffect effect) {
        super(effect);
    }

    @Override
    public AddleEffect copy() {
        return new AddleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChoiceColor choice = new ChoiceColor();
        if (controller != null && controller.choose(outcome, choice, game)) {
            ObjectColor color = choice.getColor();
            game.informPlayers(controller.getLogName() + " chooses " + color + '.');
            FilterCard filter = new FilterCard();
            filter.add(new ColorPredicate(color));
            Effect effect = new DiscardCardYouChooseTargetEffect(filter);
            return effect.apply(game, source);
        }
        return false;
    }
}
