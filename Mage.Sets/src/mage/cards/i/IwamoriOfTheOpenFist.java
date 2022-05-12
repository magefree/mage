
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class IwamoriOfTheOpenFist extends CardImpl {

    public IwamoriOfTheOpenFist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Iwamori of the Open Fist enters the battlefield, each opponent may put a legendary creature card from their hand onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IwamoriOfTheOpenFistEffect(), false));
    }

    private IwamoriOfTheOpenFist(final IwamoriOfTheOpenFist card) {
        super(card);
    }

    @Override
    public IwamoriOfTheOpenFist copy() {
        return new IwamoriOfTheOpenFist(this);
    }
}

class IwamoriOfTheOpenFistEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("legendary creature card");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
    }

    public IwamoriOfTheOpenFistEffect() {
        super(Outcome.Detriment);
        this.staticText = "each opponent may put a legendary creature card from their hand onto the battlefield";
    }

    public IwamoriOfTheOpenFistEffect(final IwamoriOfTheOpenFistEffect effect) {
        super(effect);
    }

    @Override
    public IwamoriOfTheOpenFistEffect copy() {
        return new IwamoriOfTheOpenFistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl();
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                Target target = new TargetCardInHand(filter);
                if (opponent != null && target.canChoose(opponent.getId(), source, game)) {
                    if (opponent.chooseUse(Outcome.PutCreatureInPlay, "Put a legendary creature card from your hand onto the battlefield?", source, game)) {
                        if (target.chooseTarget(Outcome.PutCreatureInPlay, opponent.getId(), source, game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                cards.add(card);
                            }
                        }
                    }
                }
            }
            controller.moveCards(cards.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            return true;
        }

        return false;
    }
}
