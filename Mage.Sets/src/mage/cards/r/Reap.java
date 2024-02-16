
package mage.cards.r;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Reap extends CardImpl {

    public Reap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Return up to X target cards from your graveyard to your hand, where X is the number of black permanents target opponent controls as you cast Reap.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect().setText("Return up to X target cards from your graveyard to your hand, where X is the number of black permanents target opponent controls as you cast this spell"));
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 0));
        this.getSpellAbility().setTargetAdjuster(ReapAdjuster.instance);
    }

    private Reap(final Reap card) {
        super(card);
    }

    @Override
    public Reap copy() {
        return new Reap(this);
    }
}

enum ReapAdjuster implements TargetAdjuster {
    instance;
    private static final FilterPermanent filter = new FilterPermanent("black permanents");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller != null) {
            ability.getTargets().clear();
            UUID opponentId = null;
            Target target = new TargetOpponent();
            if (controller.chooseTarget(Outcome.ReturnToHand, target, ability, game)) {
                opponentId = target.getFirstTarget();
            }
            int numbTargets = game.getBattlefield().getAllActivePermanents(filter, opponentId, game).size();
            ability.addTarget(new TargetCardInYourGraveyard(0, numbTargets, new FilterCard("card" + (numbTargets == 1 ? "" : "s") + " from your graveyard")));
        }
    }
}