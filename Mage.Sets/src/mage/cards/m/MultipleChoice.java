package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Elemental44Token;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MultipleChoice extends CardImpl {

    public MultipleChoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}");

        // If X is 1, scry 1, then draw a card.
        // If X is 2, you may choose a player. They return a creature they control to its owner's hand.
        // If X is 3, create a 4/4 blue and red Elemental creature token.
        // If X is 4 or more, do all of the above.
        this.getSpellAbility().addEffect(new MultipleChoiceEffect());
    }

    private MultipleChoice(final MultipleChoice card) {
        super(card);
    }

    @Override
    public MultipleChoice copy() {
        return new MultipleChoice(this);
    }
}

class MultipleChoiceEffect extends OneShotEffect {

    MultipleChoiceEffect() {
        super(Outcome.Benefit);
        staticText = "If X is 1, scry 1, then draw a card.<br>" +
                "If X is 2, you may choose a player. They return a creature they control to its owner's hand.<br>" +
                "If X is 3, create a 4/4 blue and red Elemental creature token.<br>" +
                "If X is 4 or more, do all of the above.";
    }

    private MultipleChoiceEffect(final MultipleChoiceEffect effect) {
        super(effect);
    }

    @Override
    public MultipleChoiceEffect copy() {
        return new MultipleChoiceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        if (xValue == 1 || xValue >= 4) {
            controller.scry(1, source, game);
            controller.drawCards(1, source, game);
        }
        if (xValue == 3 || xValue >= 4) {
            new Elemental44Token().putOntoBattlefield(1, game, source, source.getControllerId());
        }
        if (xValue != 2 && xValue < 4) {
            return true;
        }
        TargetPlayer targetPlayer = new TargetPlayer(0, 1, true);
        controller.choose(Outcome.Detriment, targetPlayer, source, game);
        Player player = game.getPlayer(targetPlayer.getFirstTarget());
        if (player == null || game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                player.getId(), source, game
        ) <= 0) {
            return true;
        }
        TargetPermanent targetPermanent = new TargetControlledCreaturePermanent();
        targetPermanent.setNotTarget(true);
        player.choose(Outcome.ReturnToHand, targetPermanent, source, game);
        Permanent permanent = game.getPermanent(targetPermanent.getFirstTarget());
        return permanent == null || player.moveCards(permanent, Zone.HAND, source, game);
    }
}
