package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.BeastToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author zeffirojoe
 */
public final class WildEndeavor extends CardImpl {

    public WildEndeavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.SORCERY }, "{4}{G}{G}");

        // Roll two d4 and choose one result. Create a number of 3/3 green Beast
        // creature tokens equal to that result. Then search your library for a number
        // of basic land cards equal to the other result, put them onto the battlefield
        // tapped, then shuffle.
        this.getSpellAbility().addEffect(new WildEndeavorEffect());

    }

    private WildEndeavor(final WildEndeavor card) {
        super(card);
    }

    @Override
    public WildEndeavor copy() {
        return new WildEndeavor(this);
    }
}

class WildEndeavorEffect extends OneShotEffect {

    WildEndeavorEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Roll two d4 and choose one result. Create a number of 3/3 green Beast creature tokens equal to that result"
                + "Then search your library for a number of basic land cards equal to the other result, put them onto the battlefield tapped, then shuffle.";
    }

    private WildEndeavorEffect(final WildEndeavorEffect effect) {
        super(effect);
    }

    @Override
    public WildEndeavorEffect copy() {
        return new WildEndeavorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Roll two four-sided dice
            int dice1 = controller.rollDice(source, game, 4);
            int dice2 = controller.rollDice(source, game, 4);

            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose from the D4 rolls");
            choice.getChoices()
                    .add(String.format(("Create %s 3/3 Beast token(s) and search for %s basic land(s)"), dice1, dice2));
            choice.getChoices()
                    .add(String.format(("Create %s 3/3 Beast token(s) and search for %s basic land(s)"), dice2, dice1));
            if (controller.choose(outcome, choice, game)) {
                final String chosen = choice.getChoice();
                if (!chosen.equals(choice.getChoices().iterator().next())) { // Just re-use the dice rolls for choices,
                                                                             // swap them if the second choice is picked
                    int tmp = dice1;
                    dice1 = dice2;
                    dice2 = tmp;
                }

                Effect effect = new CreateTokenTargetEffect(new BeastToken(), dice1);
                effect.setTargetPointer(new FixedTarget(controller.getId()));
                effect.apply(game, source);

                TargetCardInLibrary target = new TargetCardInLibrary(0, dice2, StaticFilters.FILTER_CARD_BASIC_LAND);
                if (controller.searchLibrary(target, source, game)) {
                    if (!target.getTargets().isEmpty()) {
                        controller.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game, true, false, false, null);
                        controller.shuffleLibrary(source, game);
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
