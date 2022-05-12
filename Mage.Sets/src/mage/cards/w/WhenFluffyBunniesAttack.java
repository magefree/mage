package mage.cards.w;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author L_J
 */
public final class WhenFluffyBunniesAttack extends CardImpl {

    public WhenFluffyBunniesAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Target creature gets -X/-X until end of turn, where X is the number of times the letter of your choice appears in that creature’s name.
        this.getSpellAbility().addEffect(new WhenFluffyBunniesAttackEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WhenFluffyBunniesAttack(final WhenFluffyBunniesAttack card) {
        super(card);
    }

    @Override
    public WhenFluffyBunniesAttack copy() {
        return new WhenFluffyBunniesAttack(this);
    }

}

class WhenFluffyBunniesAttackEffect extends OneShotEffect {

    public WhenFluffyBunniesAttackEffect() {
        super(Outcome.Detriment);
        staticText = "Target creature gets -X/-X until end of turn, where X is the number of times the letter of your choice appears in that creature's name";
    }

    public WhenFluffyBunniesAttackEffect(final WhenFluffyBunniesAttackEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());

        ChoiceImpl choice = new ChoiceImpl(true);
        choice.setMessage("Choose letter");
        Set<String> choices = new HashSet<>();
        for (Character letter = 'A'; letter <= 'Z'; letter++) {
            choices.add(letter.toString());
        }
        choice.setChoices(choices);

        if (controller != null && permanent != null && controller.choose(outcome, choice, game)) {
            if (!game.isSimulation()) {
                MageObject mageObject = game.getObject(source);
                if (mageObject != null) {
                    game.informPlayers(mageObject.getLogName() + ": " + controller.getLogName() + " has chosen " + choice.getChoice());
                }
            }

            Character chosenLetter = choice.getChoice().charAt(0);
            int unboostValue = 0;
            String permName = permanent.getName();
            for (int i = 0; i < permName.length(); i++) {
                Character letter = permName.charAt(i);
                if (Character.isLetter(letter) && Character.toUpperCase(letter) == chosenLetter) {
                    unboostValue--;
                }
            }
            BoostTargetEffect effect = new BoostTargetEffect(unboostValue, unboostValue, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }

    @Override
    public WhenFluffyBunniesAttackEffect copy() {
        return new WhenFluffyBunniesAttackEffect(this);
    }
}
