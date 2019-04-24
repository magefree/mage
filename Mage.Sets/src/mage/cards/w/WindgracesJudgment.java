package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class WindgracesJudgment extends CardImpl {

    public WindgracesJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{G}");

        // For any number of opponents, destroy target nonland permanent that player controls.
        this.getSpellAbility().addEffect(
                new DestroyTargetEffect(false, true).
                        setText("For any number of opponents, "
                                + "destroy target nonland permanent "
                                + "that player controls")
        );
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            game.getOpponents(ability.getControllerId()).forEach(playerId -> {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    FilterNonlandPermanent filter = new FilterNonlandPermanent(
                            "nonland permanent controlled by "
                            + player.getLogName()
                    );
                    filter.add(new ControllerIdPredicate(playerId));
                    ability.addTarget(new TargetPermanent(0, 1, filter, false));
                }
            });
        }
    }

    public WindgracesJudgment(final WindgracesJudgment card) {
        super(card);
    }

    @Override
    public WindgracesJudgment copy() {
        return new WindgracesJudgment(this);
    }
}
