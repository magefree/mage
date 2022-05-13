
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author LevelX2
 */
public final class UlamogsDespoiler extends CardImpl {

    public UlamogsDespoiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.PROCESSOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // As Ulamog's Despoiler enters the battlefield, you may put two cards your opponents own from exile into their owners' graveyards. If you do, Ulamog's Despoiler enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new UlamogsDespoilerEffect(), null,
                "As {this} enters the battlefield, you may put two cards your opponents own from exile into their owners' graveyards. If you do, {this} enters the battlefield with four +1/+1 counters on it", null));
    }

    private UlamogsDespoiler(final UlamogsDespoiler card) {
        super(card);
    }

    @Override
    public UlamogsDespoiler copy() {
        return new UlamogsDespoiler(this);
    }
}

class UlamogsDespoilerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("cards your opponents own from exile");

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    public UlamogsDespoilerEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may put two cards your opponents own from exile into their owners' graveyards. If you do, {this} enters the battlefield with four +1/+1 counters on it";
    }

    public UlamogsDespoilerEffect(final UlamogsDespoilerEffect effect) {
        super(effect);
    }

    @Override
    public UlamogsDespoilerEffect copy() {
        return new UlamogsDespoilerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInExile(2, 2, filter, null);
            if (target.canChoose(source.getControllerId(), source, game)) {
                if (controller.chooseTarget(outcome, target, source, game)) {
                    Cards cardsToGraveyard = new CardsImpl(target.getTargets());
                    controller.moveCards(cardsToGraveyard, Zone.GRAVEYARD, source, game);
                    return new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
