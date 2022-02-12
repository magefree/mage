package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class EtrataTheSilencer extends CardImpl {

    public EtrataTheSilencer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Etrata, the Silencer can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Whenever Etrata deals combat damage to a player, exile target creature that player controls and put a hit counter on that card. That player loses the game if they own three or more exiled card with hit counters on them. Etrata's owner shuffles Etrata into their library.
        this.addAbility(new EtrataTheSilencerTriggeredAbility());
    }

    private EtrataTheSilencer(final EtrataTheSilencer card) {
        super(card);
    }

    @Override
    public EtrataTheSilencer copy() {
        return new EtrataTheSilencer(this);
    }
}

class EtrataTheSilencerTriggeredAbility extends TriggeredAbilityImpl {

    public EtrataTheSilencerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EtrataTheSilencerEffect());
    }

    public EtrataTheSilencerTriggeredAbility(final EtrataTheSilencerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EtrataTheSilencerTriggeredAbility copy() {
        return new EtrataTheSilencerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
            Player opponent = game.getPlayer(event.getPlayerId());
            if (opponent != null) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
                filter.add(new ControllerIdPredicate(opponent.getId()));
                this.getTargets().clear();
                this.addTarget(new TargetCreaturePermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, "
                + "exile target creature that player controls "
                + "and put a hit counter on that card. "
                + "That player loses the game if they own three or more "
                + "exiled cards with hit counters on them. "
                + "{this}'s owner shuffles {this} into their library.";
    }
}

class EtrataTheSilencerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(CounterType.HIT.getPredicate());
    }

    public EtrataTheSilencerEffect() {
        super(Outcome.Benefit);
    }

    public EtrataTheSilencerEffect(final EtrataTheSilencerEffect effect) {
        super(effect);
    }

    @Override
    public EtrataTheSilencerEffect copy() {
        return new EtrataTheSilencerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(creature.getControllerId());
        if (controller == null || player == null) {
            return false;
        }
        controller.moveCards(creature, Zone.EXILED, source, game);
        Card card = game.getCard(source.getFirstTarget());
        if (card != null) {
            card.addCounters(CounterType.HIT.createInstance(), source.getControllerId(), source, game);
        }
        int cardsFound = 0;
        cardsFound = game.getExile().getAllCards(game).stream().filter((exiledCard) -> (exiledCard.getCounters(game).getCount(CounterType.HIT) >= 1
                && exiledCard.getOwnerId().equals(player.getId()))).map((_item) -> 1).reduce(cardsFound, Integer::sum);
        if (cardsFound > 2) {
            player.lost(game);
        }
        Permanent etrataTheSilencer = game.getPermanent(source.getSourceId());
        if (etrataTheSilencer != null) {
            if (etrataTheSilencer.isPhasedIn()) {
                return new ShuffleIntoLibrarySourceEffect().apply(game, source);
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
