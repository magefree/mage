package mage.cards.l;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 * @author TheElk801
 */
public final class LurrusOfTheDreamDen extends CardImpl {

    public LurrusOfTheDreamDen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}{W/B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Companion — Each permanent card in your starting deck has converted mana cost 2 or less.
        this.addAbility(new CompanionAbility(LurrusOfTheDreamDenCompanionCondition.instance));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // During each of your turns, you may cast one permanent spell with converted mana cost 2 or less from your graveyard.
        this.addAbility(new SimpleStaticAbility(new LurrusOfTheDreamDenCastFromGraveyardEffect())
                .setIdentifier(MageIdentifier.LurrusOfTheDreamDenWatcher),
                new LurrusOfTheDreamDenWatcher());
    }

    private LurrusOfTheDreamDen(final LurrusOfTheDreamDen card) {
        super(card);
    }

    @Override
    public LurrusOfTheDreamDen copy() {
        return new LurrusOfTheDreamDen(this);
    }
}

enum LurrusOfTheDreamDenCompanionCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "Each permanent card in your starting deck has mana value 2 or less.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int startingHandSize) {
        return deck.stream()
                .filter(MageObject::isPermanent)
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0) <= 2;
    }
}

class LurrusOfTheDreamDenCastFromGraveyardEffect extends AsThoughEffectImpl {

    LurrusOfTheDreamDenCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit, true);
        staticText = "During each of your turns, you may cast one permanent spell with mana value 2 or less from your graveyard";
    }

    private LurrusOfTheDreamDenCastFromGraveyardEffect(final LurrusOfTheDreamDenCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public LurrusOfTheDreamDenCastFromGraveyardEffect copy() {
        return new LurrusOfTheDreamDenCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (source.isControlledBy(affectedControllerId)
                && Zone.GRAVEYARD.equals(game.getState().getZone(objectId))
                && game.isActivePlayer(source.getControllerId())) { // only during your turn
            Card objectCard = game.getCard(objectId);
            Permanent sourceObject = game.getPermanent(source.getSourceId());
            if (sourceObject != null && objectCard != null
                    && objectCard.isPermanent(game)
                    && objectCard.isOwnedBy(source.getControllerId())
                    && objectCard.getManaValue() < 3
                    && objectCard.getSpellAbility() != null
                    && objectCard.getSpellAbility().spellCanBeActivatedRegularlyNow(affectedControllerId, game)) {
                LurrusOfTheDreamDenWatcher watcher = game.getState().getWatcher(LurrusOfTheDreamDenWatcher.class);
                return watcher != null && !watcher.isAbilityUsed(new MageObjectReference(sourceObject, game));
            }
        }

        if (!objectId.equals(getTargetPointer().getFirst(game, source))) {
            return false;
        }
        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }
        LurrusOfTheDreamDenWatcher watcher = game.getState().getWatcher(LurrusOfTheDreamDenWatcher.class);
        return watcher != null && !watcher.isAbilityUsed(new MageObjectReference(source.getSourceId(), game));
    }
}

class LurrusOfTheDreamDenWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    LurrusOfTheDreamDenWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.LurrusOfTheDreamDenWatcher)) {
            usedFrom.add(event.getAdditionalReference().getApprovingMageObjectReference());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    boolean isAbilityUsed(MageObjectReference mor) {
        return usedFrom.contains(mor);
    }
}
