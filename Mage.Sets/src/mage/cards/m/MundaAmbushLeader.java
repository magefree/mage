
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class MundaAmbushLeader extends CardImpl {

    public MundaAmbushLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // <i>Rally</i>-Whenever Munda, Ambush Leader or another Ally enters the battlefield under your control, you may look at the top four cards of your library. If you do, reveal any number of Ally cards from among them, then put those cards on top of your library in any order and the rest on the bottom in any order.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new MundaAmbushLeaderEffect(), true));

    }

    private MundaAmbushLeader(final MundaAmbushLeader card) {
        super(card);
    }

    @Override
    public MundaAmbushLeader copy() {
        return new MundaAmbushLeader(this);
    }
}

class MundaAmbushLeaderEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Ally cards to reveal and put on top of your library");

    static {
        filter.add(SubType.ALLY.getPredicate());
    }

    public MundaAmbushLeaderEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may look at the top four cards of your library. If you do, reveal any number of Ally cards from among them, then put those cards on top of your library in any order and the rest on the bottom in any order";
    }

    public MundaAmbushLeaderEffect(final MundaAmbushLeaderEffect effect) {
        super(effect);
    }

    @Override
    public MundaAmbushLeaderEffect copy() {
        return new MundaAmbushLeaderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Cards allCards = new CardsImpl();
            allCards.addAll(controller.getLibrary().getTopCards(game, 4));
            controller.lookAtCards(sourceObject.getIdName(), allCards, game);
            if (!allCards.isEmpty()) {
                Cards cardsToReveal = new CardsImpl();
                TargetCard target = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, filter);
                controller.chooseTarget(outcome, allCards, target, source, game);
                cardsToReveal.addAll(target.getTargets());
                if (!cardsToReveal.isEmpty()) {
                    controller.revealCards(sourceObject.getIdName(), cardsToReveal, game, true);
                    allCards.removeAll(cardsToReveal);
                }
                controller.putCardsOnTopOfLibrary(cardsToReveal, game, source, true);
            }
            if (!allCards.isEmpty()) {
                controller.putCardsOnBottomOfLibrary(allCards, game, source, true);
            }
            return true;
        }
        return false;
    }
}
