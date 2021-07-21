package mage.cards.v;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.token.KnightToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author zeffirojoe
 */
public final class ValiantEndeavor extends CardImpl {

    public ValiantEndeavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.SORCERY }, "{4}{W}{W}");

        // Roll two d6 and choose on result. Destroy each creature with power greater
        // than or equal to that result. Then create a number of 2/2 white Knight
        // creature tokens with vigilance equal to the other result.
        this.getSpellAbility().addEffect(new ValiantEndeavorEffect());
    }

    private ValiantEndeavor(final ValiantEndeavor card) {
        super(card);
    }

    @Override
    public ValiantEndeavor copy() {
        return new ValiantEndeavor(this);
    }
}

class ValiantEndeavorEffect extends OneShotEffect {

    ValiantEndeavorEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Roll two d6 and choose one result. Destroy each creature with power greater than or equal to that result."
                + "Then create a number of 2/2/ white Knight creature tokens with vigilance equal to the other result.";
    }

    private ValiantEndeavorEffect(final ValiantEndeavorEffect effect) {
        super(effect);
    }

    @Override
    public ValiantEndeavorEffect copy() {
        return new ValiantEndeavorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Roll two siz-sided dice
            int dice1 = controller.rollDice(source, game, 6);
            int dice2 = controller.rollDice(source, game, 6);

            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose from the D6 rolls");
            choice.getChoices().add(String.format(
                    ("Destroy each creature with %s or more power, then create %s 2/2 white Knight token(s) with vigilance"),
                    dice1, dice2));
            choice.getChoices().add(String.format(
                    ("Destroy each creature with %s or more power, then create %s 2/2 white Knight token(s) with vigilance"),
                    dice2, dice1));
            if (controller.choose(outcome, choice, game)) {
                final String chosen = choice.getChoice();
                if (!chosen.equals(choice.getChoices().iterator().next())) { // Just re-use the dice rolls for choices,
                                                                             // swap them if the second choice is picked
                    int tmp = dice1;
                    dice1 = dice2;
                    dice2 = tmp;
                }

                final FilterCreaturePermanent filter = new FilterCreaturePermanent(
                        String.format("creatures with power greater than or equal to %s", dice1));
                filter.add(new PowerPredicate(ComparisonType.MORE_THAN, dice1 - 1));

                Effect wrathEffect = new DestroyAllEffect(filter);
                wrathEffect.apply(game, source);

                Effect tokenEffect = new CreateTokenTargetEffect(new KnightToken(), dice2);
                tokenEffect.setTargetPointer(new FixedTarget(controller.getId()));
                tokenEffect.apply(game, source);

                return true;
            }
        }
        return false;
    }

}
