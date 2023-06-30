package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SearchForAzcanta extends CardImpl {

    public SearchForAzcanta(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.secondSideCardClazz = mage.cards.a.AzcantaTheSunkenRuin.class;

        this.supertype.add(SuperType.LEGENDARY);

        // At the beginning of your upkeep, look at the top card of your library. You may put it into your graveyard. Then if you have seven or more cards in your graveyard, you may transform Search for Azcanta.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new SurveilEffect(1, false),
                TargetController.YOU, false
        );
        ability.addEffect(new SearchForAzcantaEffect());
        this.addAbility(new TransformAbility());
        this.addAbility(ability);
    }

    private SearchForAzcanta(final SearchForAzcanta card) {
        super(card);
    }

    @Override
    public SearchForAzcanta copy() {
        return new SearchForAzcanta(this);
    }
}

class SearchForAzcantaEffect extends OneShotEffect {

    public SearchForAzcantaEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Then if you have seven or more cards in your graveyard, you may transform {this}.";
    }

    public SearchForAzcantaEffect(final SearchForAzcantaEffect effect) {
        super(effect);
    }

    @Override
    public SearchForAzcantaEffect copy() {
        return new SearchForAzcantaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return controller != null
                && permanent != null
                && controller.getGraveyard().size() >= 7
                && controller.chooseUse(outcome, "Transform " + permanent.getName() + '?', source, game)
                && permanent.transform(source, game);
    }
}