package mage.cards.w;

import java.util.List;
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
            List<Integer> results = controller.rollDice(source, game, 4, 2);
            int firstResult = results.get(0);
            int secondResult = results.get(1);
            int first, second;
            if (firstResult != secondResult && controller.chooseUse(outcome,
                    "Choose the amount of 3/3 green Beast creature tokens.",
                    "The other number is the amount of basic lands you search from your library and put on the battlefield tapped.",
                    "" + firstResult, "" + secondResult, source, game)) {
                first = firstResult;
                second = secondResult;
            } else {
                first = secondResult;
                second = firstResult;
            }

            Effect effect = new CreateTokenTargetEffect(new BeastToken(), first);
            effect.setTargetPointer(new FixedTarget(controller.getId()));
            effect.apply(game, source);

            TargetCardInLibrary target = new TargetCardInLibrary(0, second, StaticFilters.FILTER_CARD_BASIC_LAND);
            if (controller.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    controller.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source,
                            game, true, false, false, null);
                    controller.shuffleLibrary(source, game);
                    return true;
                }
            }
        }

        return false;
    }

}
