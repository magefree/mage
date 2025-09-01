package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AidFromTheCowl extends CardImpl {

    public AidFromTheCowl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        // <i>Revolt</i> &mdash; At the beginning of your end step, if a permanent you controlled left the battlefield this turn,
        // reveal the top card of your library. If it is a permanent card, you may put it onto the battlefield. Otherwise, put it on the bottom of your library.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AidFromTheCowlEffect())
                .withInterveningIf(RevoltCondition.instance)
                .setAbilityWord(AbilityWord.REVOLT)
                .addHint(RevoltCondition.getHint()), new RevoltWatcher());
    }

    private AidFromTheCowl(final AidFromTheCowl card) {
        super(card);
    }

    @Override
    public AidFromTheCowl copy() {
        return new AidFromTheCowl(this);
    }
}

class AidFromTheCowlEffect extends OneShotEffect {

    AidFromTheCowlEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal the top card of your library. If it's a permanent card, " +
                "you may put it onto the battlefield. Otherwise, you may put it on the bottom of your library";
    }

    private AidFromTheCowlEffect(final AidFromTheCowlEffect effect) {
        super(effect);
    }

    @Override
    public AidFromTheCowlEffect copy() {
        return new AidFromTheCowlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.getLibrary().hasCards()) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        controller.revealCards(CardUtil.getSourceIdName(game, source), new CardsImpl(card), game);
        if (card.isPermanent(game) && controller.chooseUse(Outcome.Neutral, "Put " + card.getIdName() + " onto the battlefield?", source, game)) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        } else if (controller.chooseUse(Outcome.Neutral, "Put " + card.getIdName() + " on the bottom of your library?", source, game)) {
            controller.putCardsOnBottomOfLibrary(card, game, source);
        } else {
            game.informPlayers(controller.getLogName() + " puts the revealed card back to the top of the library.");
        }
        return true;
    }
}
