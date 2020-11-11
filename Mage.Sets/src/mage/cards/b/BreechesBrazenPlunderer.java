package mage.cards.b;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
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
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
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

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

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
            game.addEffect(new BreechesBrazenPlundererCastEffect(new MageObjectReference(card, game)), source);
            game.addEffect(new BreechesBrazenPlundererManaEffect().setTargetPointer(new FixedTarget(card, game)), source);
        }
        return true;
    }
}

class BreechesBrazenPlundererCastEffect extends AsThoughEffectImpl {

    private final MageObjectReference mor;

    BreechesBrazenPlundererCastEffect(MageObjectReference mor) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        this.mor = mor;
    }

    private BreechesBrazenPlundererCastEffect(final BreechesBrazenPlundererCastEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public BreechesBrazenPlundererCastEffect copy() {
        return new BreechesBrazenPlundererCastEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (mor.getCard(game) == null) {
            discard();
            return false;
        }
        return mor.refersTo(sourceId, game) && source.isControlledBy(affectedControllerId);
    }
}

class BreechesBrazenPlundererManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    BreechesBrazenPlundererManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.EndOfTurn, Outcome.Benefit);
    }

    private BreechesBrazenPlundererManaEffect(final BreechesBrazenPlundererManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public BreechesBrazenPlundererManaEffect copy() {
        return new BreechesBrazenPlundererManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        FixedTarget fixedTarget = ((FixedTarget) getTargetPointer());
        return source.isControlledBy(affectedControllerId)
                && Objects.equals(objectId, fixedTarget.getTarget())
                && game.getState().getZoneChangeCounter(objectId) <= fixedTarget.getZoneChangeCounter() + 1
                && (game.getState().getZone(objectId) == Zone.STACK || game.getState().getZone(objectId) == Zone.EXILED);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
