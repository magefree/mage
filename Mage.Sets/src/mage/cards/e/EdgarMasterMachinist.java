package mage.cards.e;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class EdgarMasterMachinist extends CardImpl {

    public EdgarMasterMachinist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Once during each of your turns, you may cast an artifact spell from your graveyard. If you cast a spell this way, that artifact enters tapped.
        Ability castAbility = new SimpleStaticAbility(new EdgarMasterMachinistCastFromGraveyardEffect());
        castAbility.setIdentifier(MageIdentifier.EdgarMasterMachinistWatcher);
        castAbility.addWatcher(new EdgarMasterMachinistWatcher());
        this.addAbility(castAbility);

        // Tools -- Whenever Edgar attacks, it gets +X/+0 until end of turn, where X is the greatest mana value among artifacts you control.
        this.addAbility(new AttacksTriggeredAbility(
                new BoostSourceEffect(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_ARTIFACTS, StaticValue.get(0), Duration.EndOfTurn, "it")
        ).withFlavorWord("Tools").addHint(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_ARTIFACTS.getHint()));
    }

    private EdgarMasterMachinist(final EdgarMasterMachinist card) {
        super(card);
    }

    @Override
    public EdgarMasterMachinist copy() {
        return new EdgarMasterMachinist(this);
    }
}

// Does double duty:
// - Keep watch of the "Once during each of your turns" usage of the ability, similar to CastFromGraveyardOnceEachTurnAbility
// - Watcher altering spells being cast with the right MageIdentifier.
//   Similar to Intrepid Paleontologist and the like for entering with counters.
class EdgarMasterMachinistWatcher extends Watcher {

    // set of each Edgar having used their ability this turn.
    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    EdgarMasterMachinistWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {

        if (GameEvent.EventType.SPELL_CAST.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.EdgarMasterMachinistWatcher)) {
            // register a spell was cast using Edgar ability
            usedFrom.add(event.getApprovingObject().getApprovingMageObjectReference());

            // Adds the continuous effect for the cast artifact to enter tapped
            Spell target = game.getSpell(event.getTargetId());
            if (target != null) {
                MageObjectReference mor = new MageObjectReference(target, game);
                // Not that happy with setting the source of the ContinuousEffect to the SpellAbility
                game.getState().addEffect(new EdgarMasterMachinistEntersTappedEffect(mor), target.getSpellAbility());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    boolean abilityUsed(MageObjectReference mor) {
        return usedFrom.contains(mor);
    }
}

class EdgarMasterMachinistCastFromGraveyardEffect extends AsThoughEffectImpl {

    private static final FilterCard filter = new FilterArtifactCard("an artifact spell");

    EdgarMasterMachinistCastFromGraveyardEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "Once during each of your turns, you may cast an artifact spell from your graveyard. "
                + "If you cast a spell this way, that artifact enters tapped.";
    }

    private EdgarMasterMachinistCastFromGraveyardEffect(final EdgarMasterMachinistCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public EdgarMasterMachinistCastFromGraveyardEffect copy() {
        return new EdgarMasterMachinistCastFromGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        throw new IllegalArgumentException("Wrong code usage: can't call applies method on empty affectedAbility");
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        EdgarMasterMachinistWatcher watcher = game.getState().getWatcher(EdgarMasterMachinistWatcher.class);
        Card cardToCast = game.getCard(objectId);
        if (controller == null || sourcePermanent == null || watcher == null || cardToCast == null) {
            return false;
        }
        if (!game.isActivePlayer(playerId) // only during your turn
                || !source.isControlledBy(playerId) // only you may cast
                || !Zone.GRAVEYARD.equals(game.getState().getZone(objectId)) // from graveyard
                || !cardToCast.getOwnerId().equals(playerId) // only your graveyard
                || !(affectedAbility instanceof SpellAbility) // characteristics to check
                || watcher.abilityUsed(new MageObjectReference(sourcePermanent, game)) // once per turn
        ) {
            return false;
        }
        SpellAbility spellAbility = (SpellAbility) affectedAbility;
        Card cardToCheck = spellAbility.getCharacteristics(game);
        if (spellAbility.getManaCosts().isEmpty()) {
            return false;
        }
        Set<MageIdentifier> allowedToBeCastNow = spellAbility.spellCanBeActivatedNow(playerId, game);
        return allowedToBeCastNow.contains(MageIdentifier.Default)
                && filter.match(cardToCheck, playerId, source, game);
    }
}

class EdgarMasterMachinistEntersTappedEffect extends EntersBattlefieldEffect {

    private final MageObjectReference mor;

    EdgarMasterMachinistEntersTappedEffect(MageObjectReference mor) {
        super(new TapSourceEffect(true));
        this.mor = mor;
        this.duration = Duration.EndOfTurn; // effect cleanup at end of turn if not consumed.
    }

    private EdgarMasterMachinistEntersTappedEffect(final EdgarMasterMachinistEntersTappedEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public EdgarMasterMachinistEntersTappedEffect copy() {
        return new EdgarMasterMachinistEntersTappedEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!super.applies(event, source, game)) {
            return false;
        }

        EntersTheBattlefieldEvent zEvent = (EntersTheBattlefieldEvent) event;
        // checks that the entering permanent is coming from the stack and that it is the looked after spell.
        return Zone.STACK.equals(zEvent.getFromZone())
                && !game.getStack().isEmpty()
                && mor.equals(new MageObjectReference(game.getStack().getFirst(), game));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return super.replaceEvent(event, source, game);
    }
}
