package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZarethSanTheTrickster extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ROGUE, "an unblocked attacking Rogue you control");

    static {
        filter.add(UnblockedPredicate.instance);
    }

    public ZarethSanTheTrickster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // {2}{U}{B}, Return an unblocked attacking Rogue you control to its owner's hand: Put Zareth San, the Trickster from your hand onto the battlefield tapped and attacking.
        Ability ability = new SimpleActivatedAbility(
                Zone.HAND, new ZarethSanTheTricksterEffect(), new ManaCostsImpl<>("{2}{U}{B}")
        );
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);

        // Whenever Zareth San deals combat damage to a player, you may put target permanent card from that player's graveyard onto the battlefield under your control.
        this.addAbility(new ZarethSanTheTricksterTriggeredAbility());
    }

    private ZarethSanTheTrickster(final ZarethSanTheTrickster card) {
        super(card);
    }

    @Override
    public ZarethSanTheTrickster copy() {
        return new ZarethSanTheTrickster(this);
    }
}

class ZarethSanTheTricksterEffect extends OneShotEffect {

    ZarethSanTheTricksterEffect() {
        super(Outcome.Benefit);
        staticText = "Put {this} from your hand onto the battlefield tapped and attacking.";
    }

    private ZarethSanTheTricksterEffect(final ZarethSanTheTricksterEffect effect) {
        super(effect);
    }

    @Override
    public ZarethSanTheTricksterEffect copy() {
        return new ZarethSanTheTricksterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getHand().get(source.getSourceId(), game);
        if (card == null) {
            return true;
        }
        controller.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, true, null
        );
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null) {
            game.getCombat().addAttackingCreature(permanent.getId(), game);
        }
        return true;
    }
}

class ZarethSanTheTricksterTriggeredAbility extends TriggeredAbilityImpl {

    ZarethSanTheTricksterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
    }

    private ZarethSanTheTricksterTriggeredAbility(final ZarethSanTheTricksterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ZarethSanTheTricksterTriggeredAbility copy() {
        return new ZarethSanTheTricksterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent == null
                || !event.getSourceId().equals(this.sourceId)
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        FilterCard filter = new FilterPermanentCard(
                "permanent card in " + opponent.getLogName() + "'s graveyard"
        );
        filter.add(new OwnerIdPredicate(opponent.getId()));
        this.getTargets().clear();
        this.addTarget(new TargetCardInGraveyard(filter));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may put target permanent card " +
                "from that player's graveyard onto the battlefield under your control.";
    }
}
