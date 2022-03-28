
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ScourgeOfFleets extends CardImpl {

    public ScourgeOfFleets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Scourge of Fleets enters the battlefield, return each creature your opponents control with toughness X or less, where X is the number of Islands you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScourgeOfFleetsEffect(), false));
    }

    private ScourgeOfFleets(final ScourgeOfFleets card) {
        super(card);
    }

    @Override
    public ScourgeOfFleets copy() {
        return new ScourgeOfFleets(this);
    }
}

class ScourgeOfFleetsEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("number of Islands you control");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public ScourgeOfFleetsEffect() {
        super(Outcome.Benefit);
        this.staticText = "return each creature your opponents control with toughness X or less to its owner's hand, where X is the number of Islands you control";
    }

    public ScourgeOfFleetsEffect(final ScourgeOfFleetsEffect effect) {
        super(effect);
    }

    @Override
    public ScourgeOfFleetsEffect copy() {
        return new ScourgeOfFleetsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int islands = game.getBattlefield().count(filter, source.getControllerId(), source, game);
            FilterPermanent creatureFilter = new FilterCreaturePermanent();
            creatureFilter.add(TargetController.OPPONENT.getControllerPredicate());
            creatureFilter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, islands + 1));
            Cards cardsToHand = new CardsImpl();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(creatureFilter, source.getControllerId(), source, game)) {
                cardsToHand.add(permanent);
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
