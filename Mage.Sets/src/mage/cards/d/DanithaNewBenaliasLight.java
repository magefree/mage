package mage.cards.d;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DanithaNewBenaliasLight extends CardImpl {

    public DanithaNewBenaliasLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Once during each of your turns, you may cast an Aura or Equipment spell from your graveyard.
        this.addAbility(new SimpleStaticAbility(
                new DanithaNewBenaliasLightCastFromGraveyardEffect()
        ).setIdentifier(MageIdentifier.DanithaNewBenaliasLightWatcher), new DanithaNewBenaliasLightWatcher());
    }

    private DanithaNewBenaliasLight(final DanithaNewBenaliasLight card) {
        super(card);
    }

    @Override
    public DanithaNewBenaliasLight copy() {
        return new DanithaNewBenaliasLight(this);
    }
}

class DanithaNewBenaliasLightCastFromGraveyardEffect extends AsThoughEffectImpl {

    DanithaNewBenaliasLightCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.PutCardInPlay);
        staticText = "once during each of your turns, you may cast an Aura or Equipment spell from your graveyard";
    }

    private DanithaNewBenaliasLightCastFromGraveyardEffect(final DanithaNewBenaliasLightCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DanithaNewBenaliasLightCastFromGraveyardEffect copy() {
        return new DanithaNewBenaliasLightCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        if (!source.isControlledBy(playerId) || game.getState().getZone(objectId) != Zone.GRAVEYARD || !(affectedAbility instanceof SpellAbility)) {
            return false;
        }
        Card objectCard = ((SpellAbility) affectedAbility).getCharacteristics(game);
        return objectCard != null
                && objectCard.isOwnedBy(source.getControllerId())
                && (objectCard.hasSubtype(SubType.AURA, game) || objectCard.hasSubtype(SubType.EQUIPMENT, game))
                && objectCard.getSpellAbility() != null
                && objectCard.getSpellAbility().spellCanBeActivatedRegularlyNow(playerId, game)
                && !DanithaNewBenaliasLightWatcher.isAbilityUsed(source, game);
    }
    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        throw new IllegalArgumentException("Wrong code usage: can't call applies method on empty affectedAbility");
    }
}

class DanithaNewBenaliasLightWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    DanithaNewBenaliasLightWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && event.hasApprovingIdentifier(MageIdentifier.DanithaNewBenaliasLightWatcher)) {
            usedFrom.add(event.getAdditionalReference().getApprovingMageObjectReference());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    public static boolean isAbilityUsed(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(DanithaNewBenaliasLightWatcher.class)
                .usedFrom
                .contains(new MageObjectReference(source.getSourceId(), game));
    }
}
