package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPlayerBatchEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreechesBrazenPlunderer extends CardImpl {

    public BreechesBrazenPlunderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever one or more Pirates you control deal damage to your opponents, exile the top card of each of those opponents' libraries. You may play those cards this turn, and you may spend mana as though it were mana of any color to cast those spells.
        this.addAbility(new BreechesBrazenPlundererTriggeredAbility());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private BreechesBrazenPlunderer(final BreechesBrazenPlunderer card) {
        super(card);
    }

    @Override
    public BreechesBrazenPlunderer copy() {
        return new BreechesBrazenPlunderer(this);
    }
}

class BreechesBrazenPlundererTriggeredAbility extends TriggeredAbilityImpl {

    BreechesBrazenPlundererTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private BreechesBrazenPlundererTriggeredAbility(final BreechesBrazenPlundererTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerBatchEvent dEvent = (DamagedPlayerBatchEvent) event;
        Set<UUID> opponents = new HashSet<>();
        for (DamagedEvent damagedEvent : dEvent.getEvents()) {
            Permanent permanent = game.getPermanent(damagedEvent.getSourceId());
            if (permanent == null
                    || !permanent.isControlledBy(getControllerId())
                    || !permanent.hasSubtype(SubType.PIRATE, game)
                    || !game.getOpponents(getControllerId()).contains(damagedEvent.getTargetId())) {
                continue;
            }
            opponents.add(damagedEvent.getTargetId());
        }
        if (opponents.size() < 1) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new BreechesBrazenPlundererEffect(opponents));
        return true;
    }

    @Override
    public BreechesBrazenPlundererTriggeredAbility copy() {
        return new BreechesBrazenPlundererTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more Pirates you control deal damage to your opponents, " +
                "exile the top card of each of those opponents' libraries. You may play those cards this turn, " +
                "and you may spend mana as though it were mana of any color to cast those spells.";
    }
}

class BreechesBrazenPlundererEffect extends OneShotEffect {

    private final Set<UUID> opponentIds = new HashSet<>();

    BreechesBrazenPlundererEffect(Set<UUID> opponentIds) {
        super(Outcome.Benefit);
        this.opponentIds.addAll(opponentIds);
    }

    private BreechesBrazenPlundererEffect(final BreechesBrazenPlundererEffect effect) {
        super(effect);
        this.opponentIds.addAll(effect.opponentIds);
    }

    @Override
    public BreechesBrazenPlundererEffect copy() {
        return new BreechesBrazenPlundererEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        opponentIds
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getLibrary)
                .map(library -> library.getFromTop(game))
                .forEach(cards::add);
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.EXILED);
        if (cards.isEmpty()) {
            return false;
        }
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, true);
        }
        return true;
    }
}