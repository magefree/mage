
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class ChoiceOfDamnations extends CardImpl {

    public ChoiceOfDamnations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{B}");
        this.subtype.add(SubType.ARCANE);

        // Target opponent chooses a number. You may have that player lose that much life. If you don't, that player sacrifices all but that many permanents.
        this.getSpellAbility().addEffect(new ChoiceOfDamnationsEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public ChoiceOfDamnations(final ChoiceOfDamnations card) {
        super(card);
    }

    @Override
    public ChoiceOfDamnations copy() {
        return new ChoiceOfDamnations(this);
    }
}

class ChoiceOfDamnationsEffect extends OneShotEffect {

    public ChoiceOfDamnationsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent chooses a number. You may have that player lose that much life. If you don't, that player sacrifices all but that many permanents";
    }

    public ChoiceOfDamnationsEffect(final ChoiceOfDamnationsEffect effect) {
        super(effect);
    }

    @Override
    public ChoiceOfDamnationsEffect copy() {
        return new ChoiceOfDamnationsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            int amount = targetPlayer.getAmount(0, Integer.MAX_VALUE, "Chooses a number", game);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                if (controller.chooseUse(outcome, "Shall " + targetPlayer.getLogName() + " lose " + amount + " life?", source, game)) {
                    targetPlayer.loseLife(amount, game, false);
                } else {
                    int numberPermanents = game.getState().getBattlefield().countAll(new FilterPermanent(), targetPlayer.getId(), game);
                    if (numberPermanents > amount) {
                        int numberToSacrifice = numberPermanents - amount;
                        Target target = new TargetControlledPermanent(numberToSacrifice, numberToSacrifice, new FilterControlledPermanent("permanent you control to sacrifice"), false);
                        targetPlayer.chooseTarget(Outcome.Sacrifice, target, source, game);
                        for (UUID uuid : target.getTargets()) {
                            Permanent permanent = game.getPermanent(uuid);
                            if (permanent != null) {
                                permanent.sacrifice(source.getSourceId(), game);
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
