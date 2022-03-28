package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author North
 */
public final class FoldIntoAether extends CardImpl {

    public FoldIntoAether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Counter target spell. If that spell is countered this way, its controller may put a creature card from their hand onto the battlefield.
        this.getSpellAbility().addEffect(new FoldIntoAetherEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private FoldIntoAether(final FoldIntoAether card) {
        super(card);
    }

    @Override
    public FoldIntoAether copy() {
        return new FoldIntoAether(this);
    }
}

class FoldIntoAetherEffect extends OneShotEffect {

    public FoldIntoAetherEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell. If that spell is countered this way, its controller may put a creature card from their hand onto the battlefield";
    }

    public FoldIntoAetherEffect(final FoldIntoAetherEffect effect) {
        super(effect);
    }

    @Override
    public FoldIntoAetherEffect copy() {
        return new FoldIntoAetherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        StackObject stackObject = game.getStack().getStackObject(targetId);
        Player spellController = null;
        if (stackObject != null) {
            spellController = game.getPlayer(stackObject.getControllerId());
        }
        if (game.getStack().counter(targetId, source, game)) {
            TargetCardInHand target = new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE);
            if (spellController != null
                    && target.canChoose(spellController.getId(), source, game)
                    && spellController.chooseUse(Outcome.Neutral, "Put a creature card from your hand in play?", source, game)
                    && spellController.choose(Outcome.PutCreatureInPlay, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    spellController.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
