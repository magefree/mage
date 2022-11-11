package mage.cards.t;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrailOfCrumbs extends CardImpl {

    public TrailOfCrumbs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // When Trail of Crumbs enters the battlefield, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Whenever you sacrifice a Food, you may pay {1}. If you do, look at the top two cards of your library.
        // You may reveal a permanent card from among them and put it into your hand.
        // Put the rest on the bottom of your library in any order.
        this.addAbility(new TrailOfCrumbsTriggeredAbility());
    }

    private TrailOfCrumbs(final TrailOfCrumbs card) {
        super(card);
    }

    @Override
    public TrailOfCrumbs copy() {
        return new TrailOfCrumbs(this);
    }
}

class TrailOfCrumbsTriggeredAbility extends TriggeredAbilityImpl {

    TrailOfCrumbsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new LookLibraryAndPickControllerEffect(
                2, 1, StaticFilters.FILTER_CARD_PERMANENT, PutCards.HAND, PutCards.BOTTOM_ANY
        ), new GenericManaCost(1)));
        setTriggerPhrase("Whenever you sacrifice a Food, ");
    }

    private TrailOfCrumbsTriggeredAbility(final TrailOfCrumbsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TrailOfCrumbsTriggeredAbility copy() {
        return new TrailOfCrumbsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return permanent != null
                && event.getPlayerId().equals(this.getControllerId())
                && permanent.hasSubtype(SubType.FOOD, game);
    }
}
