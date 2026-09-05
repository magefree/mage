package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author muz
 */
public final class ThranduilsDecree extends CardImpl {

    public ThranduilsDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Counter target spell. If a permanent spell is countered this way, exile it instead of putting it into its owner's graveyard. You may cast that card without paying its mana cost for as long as it remains exiled.
        this.getSpellAbility().addEffect(new ThranduilsDecreeEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private ThranduilsDecree(final ThranduilsDecree card) {
        super(card);
    }

    @Override
    public ThranduilsDecree copy() {
        return new ThranduilsDecree(this);
    }
}


class ThranduilsDecreeEffect extends OneShotEffect {

    ThranduilsDecreeEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Counter target spell. If a permanent spell is countered this way, exile it instead of putting it into its owner's graveyard. You may cast that card without paying its mana cost for as long as it remains exiled";
    }

    private ThranduilsDecreeEffect(final ThranduilsDecreeEffect effect) {
        super(effect);
    }

    @Override
    public ThranduilsDecreeEffect copy() {
        return new ThranduilsDecreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        UUID targetId = getTargetPointer().getFirst(game, source);
        StackObject stackObject = game.getStack().getStackObject(targetId);
        if (stackObject != null && stackObject.isPermanent()) {
            if (game.getStack().counter(targetId, source, game, PutCards.EXILED)) {
                Card card = ((Spell) stackObject).getCard();
                if (card != null) {
                    ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, TargetController.YOU, Duration.Custom, true);
                    effect.setTargetPointer(new FixedTarget(card.getId(), game.getState().getZoneChangeCounter(card.getId())));
                    game.addEffect(effect, source);
                }
            }
        } else {
            game.getStack().counter(targetId, source, game);
        }

        return true;
    }
}
