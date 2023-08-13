package mage.cards.g;

import java.util.HashSet;
import java.util.Set;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.UUID;
import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class GisaAndGeralf extends CardImpl {

    public GisaAndGeralf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Gisa and Geralf enters the battlefield, put the top four cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(4)));

        // During each of your turns, you may cast a Zombie creature card from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GisaAndGeralfCastFromGraveyardEffect())
                .setIdentifier(MageIdentifier.GisaAndGeralfWatcher), 
                new GisaAndGeralfWatcher());
    }

    private GisaAndGeralf(final GisaAndGeralf card) {
        super(card);
    }

    @Override
    public GisaAndGeralf copy() {
        return new GisaAndGeralf(this);
    }
}

class GisaAndGeralfCastFromGraveyardEffect extends AsThoughEffectImpl {

    GisaAndGeralfCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.PutCreatureInPlay, true);
        staticText = "During each of your turns, you may cast a Zombie creature spell from your graveyard";
    }

    GisaAndGeralfCastFromGraveyardEffect(final GisaAndGeralfCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GisaAndGeralfCastFromGraveyardEffect copy() {
        return new GisaAndGeralfCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (source.isControlledBy(affectedControllerId)
                && Zone.GRAVEYARD.equals(game.getState().getZone(objectId))) {
            Card objectCard = game.getCard(objectId);
            Permanent sourceObject = game.getPermanent(source.getSourceId());
            if (sourceObject != null && objectCard != null
                    && objectCard.isOwnedBy(source.getControllerId())
                    && objectCard.isCreature(game)
                    && objectCard.hasSubtype(SubType.ZOMBIE, game)
                    && objectCard.getSpellAbility() != null
                    && objectCard.getSpellAbility().spellCanBeActivatedRegularlyNow(affectedControllerId, game)) {
                GisaAndGeralfWatcher watcher = game.getState().getWatcher(GisaAndGeralfWatcher.class);
                return watcher != null && !watcher.isAbilityUsed(new MageObjectReference(sourceObject, game));
            }
        }
        return false;
    }
}

class GisaAndGeralfWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    GisaAndGeralfWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.GisaAndGeralfWatcher)) {
            usedFrom.add(event.getAdditionalReference().getApprovingMageObjectReference());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    public boolean isAbilityUsed(MageObjectReference mor) {
        return usedFrom.contains(mor);
    }
}
