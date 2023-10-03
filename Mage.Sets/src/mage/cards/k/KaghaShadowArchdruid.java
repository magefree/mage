package mage.cards.k;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author Fyusel
 */
public final class KaghaShadowArchdruid extends CardImpl {

    public KaghaShadowArchdruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever Kagha, Shadow Archdruid attacks, it gains deathtouch until end of turn. Mill two cards.
        Ability ability = new AttacksTriggeredAbility(new GainAbilitySourceEffect(
            DeathtouchAbility.getInstance(),
            Duration.EndOfTurn));
        ability.addEffect(new MillCardsControllerEffect(2));
        this.addAbility(ability);

        // Once during each of your turns, you may play a land or cast a permanent spell from among cards in your graveyard that were put there from your library this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KaghaShadowArchdruidEffect())
            .setIdentifier(MageIdentifier.KaghaShadowArchdruidWatcher),
            new KaghaShadowArchdruidWatcher());
    }

    private KaghaShadowArchdruid(final KaghaShadowArchdruid card) {
        super(card);
    }

    @Override
    public KaghaShadowArchdruid copy() {
        return new KaghaShadowArchdruid(this);
    }
}

class KaghaShadowArchdruidEffect extends AsThoughEffectImpl {

    KaghaShadowArchdruidEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "Once during each of your turns, you may play a land or cast a permanent spell from among cards in your graveyard that were put there from your library this turn.";
    }

    private KaghaShadowArchdruidEffect(final KaghaShadowArchdruidEffect effect) {
        super(effect);
    }

    @Override
    public KaghaShadowArchdruidEffect copy() {
        return new KaghaShadowArchdruidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (source.isControlledBy(affectedControllerId)
                && Zone.GRAVEYARD.equals(game.getState().getZone(objectId))
                && game.isActivePlayer(affectedControllerId)) {
            Card card = game.getCard(objectId);
            Permanent sourceObject = source.getSourcePermanentIfItStillExists(game);
            KaghaShadowArchdruidWatcher watcher = game.getState().getWatcher(KaghaShadowArchdruidWatcher.class);
            if (card != null && sourceObject != null 
                    && card.isOwnedBy(affectedControllerId)
                    && card.isPermanent(game)
                    && watcher != null
                    && watcher.checkCard(card, game)) {
                return watcher.abilityNotUsed(new MageObjectReference(sourceObject, game));
            }
        }
        return false;
    }
}

class KaghaShadowArchdruidWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();
    private final Set<MageObjectReference> morSet = new HashSet<>();

    KaghaShadowArchdruidWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if ((GameEvent.EventType.SPELL_CAST.equals(event.getType()) || GameEvent.EventType.LAND_PLAYED.equals(event.getType()))
                && event.hasApprovingIdentifier(MageIdentifier.KaghaShadowArchdruidWatcher)) {
            usedFrom.add(event.getAdditionalReference().getApprovingMageObjectReference());
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.LIBRARY && zEvent.getToZone() == Zone.GRAVEYARD) {
                morSet.add(new MageObjectReference(zEvent.getTargetId(), game));
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
        morSet.clear();
    }

    boolean abilityNotUsed(MageObjectReference mor) {
        return !usedFrom.contains(mor);
    }

    boolean checkCard(Card card, Game game) {
        return morSet
                .stream()
                .anyMatch(mor -> mor.refersTo(card, game));
    }
}
