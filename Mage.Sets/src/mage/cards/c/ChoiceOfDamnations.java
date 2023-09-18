package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ChoiceOfDamnations extends CardImpl {

    public ChoiceOfDamnations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}");
        this.subtype.add(SubType.ARCANE);

        // Target opponent chooses a number. You may have that player lose that much life. If you don't, that player sacrifices all but that many permanents.
        this.getSpellAbility().addEffect(new ChoiceOfDamnationsEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private ChoiceOfDamnations(final ChoiceOfDamnations card) {
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

    private ChoiceOfDamnationsEffect(final ChoiceOfDamnationsEffect effect) {
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
            int numberPermanents = game.getState().getBattlefield().countAll(new FilterPermanent(), targetPlayer.getId(), game);

            // AI hint
            int amount;
            if (targetPlayer.isComputer()) {
                // AI as defender
                int safeLifeToLost = Math.max(0, targetPlayer.getLife() / 2);
                amount = Math.min(numberPermanents, safeLifeToLost);
            } else {
                // Human must choose
                amount = targetPlayer.getAmount(0, Integer.MAX_VALUE, "Chooses a number", game);
            }

            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {

                // AI hint
                boolean chooseLoseLife;
                if (targetPlayer.isComputer()) {
                    // AI as attacker
                    chooseLoseLife = (numberPermanents == 0 || amount <= numberPermanents || targetPlayer.getLife() < amount);
                } else {
                    // Human must choose
                    chooseLoseLife = controller.chooseUse(outcome, "Shall " + targetPlayer.getLogName() + " lose " + amount + " life?", source, game);
                }

                if (chooseLoseLife) {
                    targetPlayer.loseLife(amount, game, source, false);
                } else {
                    // rules:
                    // If the opponent must sacrifice all but a number of permanents, that opponent chooses that many
                    // permanents and then sacrifices the rest. If the number chosen is greater than the number of
                    // permanents the opponent controls, the player sacrifices nothing.
                    // (2005-06-01)
                    if (numberPermanents > amount) {
                        int numberToSacrifice = numberPermanents - amount;
                        Target target = new TargetControlledPermanent(numberToSacrifice, numberToSacrifice, new FilterControlledPermanent("permanent you control to sacrifice"), false);
                        targetPlayer.chooseTarget(Outcome.Sacrifice, target, source, game);
                        for (UUID uuid : target.getTargets()) {
                            Permanent permanent = game.getPermanent(uuid);
                            if (permanent != null) {
                                permanent.sacrifice(source, game);
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
