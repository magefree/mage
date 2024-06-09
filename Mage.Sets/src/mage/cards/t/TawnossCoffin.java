package mage.cards.t;

import java.util.ArrayList;
import java.util.HashMap;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class TawnossCoffin extends CardImpl {

    public TawnossCoffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // You may choose not to untap Tawnos's Coffin during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {3}, {T}: Exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TawnossCoffinEffect(), new TapSourceCost());
        ability.addCost(new ManaCostsImpl<>("{3}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        //When Tawnos's Coffin leaves the battlefield or becomes untapped, return the exiled card to the battlefield under its owner's control tapped with the noted number and kind of counters on it, and if you do, return the exiled Aura cards to the battlefield under their owner's control attached to that permanent.
        Ability ability3 = new TawnossCoffinTriggeredAbility(new TawnossCoffinReturnEffect());
        this.addAbility(ability3);

    }

    private TawnossCoffin(final TawnossCoffin card) {
        super(card);
    }

    @Override
    public TawnossCoffin copy() {
        return new TawnossCoffin(this);
    }
}

class TawnossCoffinTriggeredAbility extends LeavesBattlefieldTriggeredAbility {

    public TawnossCoffinTriggeredAbility(Effect effect) {
        super(effect, false);
        setTriggerPhrase("When {this} leaves the battlefield or becomes untapped, ");
    }

    private TawnossCoffinTriggeredAbility(final TawnossCoffinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TawnossCoffinTriggeredAbility copy() {
        return new TawnossCoffinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return super.checkEventType(event, game)
                || event.getType() == GameEvent.EventType.UNTAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UNTAPPED) {
            return event.getTargetId().equals(getSourceId());
        } else {
            return super.checkTrigger(event, game);
        }
    }
}

class TawnossCoffinEffect extends OneShotEffect {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent();

    static {
        filter.add(SubType.AURA.getPredicate());
    }

    public TawnossCoffinEffect() {
        super(Outcome.Neutral);
        this.staticText = "exile target creature and all Auras attached to it. "
                + "Note the number and kind of counters that were on that creature";
    }

    private TawnossCoffinEffect(final TawnossCoffinEffect effect) {
        super(effect);
    }

    @Override
    public TawnossCoffinEffect copy() {
        return new TawnossCoffinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Exile creature and all Auras attached to it.
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null
                || sourceObject == null
                || creature == null) {
            return false;
        }

        UUID exileZoneId = CardUtil.getCardExileZoneId(game, source);
        // uniqueKey string allows us to track each instance separately
        String uniqueKey = exileZoneId.toString() + source.getId().toString();
        // exileData keeps track of all creatures/auras/counters and their relationship
        Map<String, Object> exileData = new HashMap<>();
        Set<Card> toExile = new HashSet<>();
        toExile.add(creature);
        Map<UUID, List<UUID>> creatureToAurasMap = new HashMap<>();
        List<UUID> auras = new ArrayList<>();
        for (UUID attachmentId : creature.getAttachments()) {
            Permanent attachment = game.getPermanent(attachmentId);
            if (attachment != null
                    && attachment.hasSubtype(SubType.AURA, game)) {
                toExile.add(attachment);
                auras.add(attachmentId);
            }
        }

        creatureToAurasMap.put(creature.getId(), auras);
        controller.moveCardsToExile(toExile, source, game, true, exileZoneId, sourceObject.getIdName());
        exileData.put("NotedCounters", creature.getCounters(game).copy());
        exileData.put("Creature", creature.getId());
        exileData.put("Auras", creatureToAurasMap);
        game.getState().setValue(uniqueKey, exileData);

        // Add unique key to list of exiled creatures
        List<String> exiledKeys = (List<String>) game.getState().getValue(exileZoneId.toString() + "ExiledKeys");
        if (exiledKeys == null) {
            exiledKeys = new ArrayList<>();
        }
        exiledKeys.add(uniqueKey);
        game.getState().setValue(exileZoneId.toString() + "ExiledKeys", exiledKeys);

        return true;
    }
}

class TawnossCoffinReturnEffect extends OneShotEffect {

    TawnossCoffinReturnEffect() {
        super(Outcome.Neutral);
        this.staticText = "return the exiled card to the battlefield under its owner's control tapped with the noted number and kind of counters on it. "
                + "If you do, return the exiled Aura cards to the battlefield under their owner's control attached to that permanent";
    }

    private TawnossCoffinReturnEffect(final TawnossCoffinReturnEffect effect) {
        super(effect);
    }

    @Override
    public TawnossCoffinReturnEffect copy() {
        return new TawnossCoffinReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        UUID exileZoneId = CardUtil.getCardExileZoneId(game, source.getSourceId(), source.getSourcePermanentIfItStillExists(game) == null);
        ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
        if (exileZone == null) {
            return true;
        }
        List<String> exiledKeys = (List<String>) game.getState().getValue(exileZoneId.toString() + "ExiledKeys");
        if (exiledKeys == null
                || exiledKeys.isEmpty()) {
            return true;
        }

        for (String uniqueKey : exiledKeys) {
            Map<String, Object> exileData = (Map<String, Object>) game.getState().getValue(uniqueKey);
            if (exileData == null) {
                continue;
            }
            UUID creatureId = (UUID) exileData.get("Creature");
            if (creatureId == null) {
                continue;
            }
            if (!exileZone.contains(creatureId)) {
                continue; // Card was removed from exile meanwhile, other cards stay in exile
            }
            Card creatureCard = game.getCard(creatureId);
            if (creatureCard == null) {
                continue;
            }
            controller.moveCards(creatureCard, Zone.BATTLEFIELD, source, game, false, false, true, null);
            Permanent newPermanent = game.getPermanent(creatureCard.getId());
            if (newPermanent == null) {
                continue;
            }
            // Add the noted counters
            Counters notedCounters = (Counters) exileData.get("NotedCounters");
            if (notedCounters != null) {
                for (Counter c : notedCounters.values()) {
                    if (c != null) {
                        newPermanent.getCounters(game).addCounter(c);
                    }
                }
            }

            // Return the exiled auras
            Map<UUID, List<UUID>> creatureToAurasMap = (Map<UUID, List<UUID>>) exileData.get("Auras");
            List<UUID> auraIds = creatureToAurasMap.get(creatureId);
            if (auraIds == null) {
                continue;
            }
            Set<Card> returningAuras = new HashSet<>();
            for (UUID auraId : auraIds) {
                Card auraCard = game.getCard(auraId);
                if (auraCard != null && exileZone.contains(auraId)) {
                    returningAuras.add(auraCard);
                    game.getState().setValue("attachTo:" + auraCard.getId(), newPermanent);
                }
            }
            controller.moveCards(returningAuras, Zone.BATTLEFIELD, source, game, false, false, true, null);
            for (Card enchantment : returningAuras) {
                Permanent permanent = game.getPermanent(enchantment.getId());
                if (permanent != null) {
                    newPermanent.addAttachment(permanent.getId(), source, game);
                }
            }
        }

        // Clear the list of exiled keys
        game.getState().setValue(exileZoneId.toString() + "ExiledKeys", new ArrayList<>());

        return true;
    }
}
