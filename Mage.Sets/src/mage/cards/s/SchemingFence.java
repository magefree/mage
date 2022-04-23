package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonlandPermanent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SchemingFence extends CardImpl {

    public SchemingFence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // As Scheming Fence enters the battlefield, you may choose a nonland permanent.
        this.addAbility(new AsEntersBattlefieldAbility(new SchemingFenceChooseEffect()));

        // Activated abilities of the chosen permanent can't be activated.
        this.addAbility(new SimpleStaticAbility(new SchemingFenceDisableEffect()));

        // Scheming Fence has all activated abilities of the chosen permanent except for loyalty abilities. You may spend mana as though it were mana of any color to activate those abilities.
        Ability ability = new SimpleStaticAbility(new SchemingFenceGainEffect());
        ability.addEffect(new SchemingFenceManaEffect());
        this.addAbility(ability);
    }

    private SchemingFence(final SchemingFence card) {
        super(card);
    }

    @Override
    public SchemingFence copy() {
        return new SchemingFence(this);
    }
}

class SchemingFenceChooseEffect extends OneShotEffect {

    public SchemingFenceChooseEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "you may choose a nonland permanent";
    }

    public SchemingFenceChooseEffect(final SchemingFenceChooseEffect effect) {
        super(effect);
    }

    @Override
    public SchemingFenceChooseEffect copy() {
        return new SchemingFenceChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent mageObject = game.getPermanentEntering(source.getSourceId());
        if (controller == null || mageObject == null) {
            return false;
        }
        TargetPermanent target = new TargetNonlandPermanent(0, 1, true);
        controller.choose(this.outcome, target, source, game);
        Permanent chosenPermanent = game.getPermanent(target.getFirstTarget());
        if (chosenPermanent == null) {
            return true;
        }
        game.getState().setValue(
                mageObject.getId() + "_chosenPermanent",
                new MageObjectReference(chosenPermanent, game)
        );
        mageObject.addInfo(
                "chosen permanent",
                CardUtil.addToolTipMarkTags(
                        "Chosen permanent: " + chosenPermanent.getIdName()
                ), game
        );
        return true;
    }
}

class SchemingFenceDisableEffect extends ContinuousRuleModifyingEffectImpl {

    public SchemingFenceDisableEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "activated abilities of the chosen permanent can't be activated";
    }

    public SchemingFenceDisableEffect(final SchemingFenceDisableEffect effect) {
        super(effect);
    }

    @Override
    public SchemingFenceDisableEffect copy() {
        return new SchemingFenceDisableEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return Optional.of(game.getState().getValue(source.getId() + "_chosenPermanent"))
                .filter(Objects::nonNull)
                .map(MageObjectReference.class::cast)
                .filter(mor -> mor.zoneCounterIsCurrent(game))
                .map(MageObjectReference::getSourceId)
                .equals(event.getSourceId());
    }
}

class SchemingFenceGainEffect extends ContinuousEffectImpl {

    SchemingFenceGainEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of the chosen permanent except for loyalty abilities.";
    }

    private SchemingFenceGainEffect(final SchemingFenceGainEffect effect) {
        super(effect);
    }

    @Override
    public SchemingFenceGainEffect copy() {
        return new SchemingFenceGainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent chosen = Optional.of(game.getState().getValue(source.getId() + "_chosenPermanent"))
                .filter(Objects::nonNull)
                .map(MageObjectReference.class::cast)
                .map(mor -> mor.getPermanent(game))
                .orElse(null);
        if (permanent == null || chosen == null) {
            return false;
        }
        for (Ability ability : chosen.getAbilities(game).getActivatedAbilities(Zone.ALL)) {
            if (ability instanceof LoyaltyAbility) {
            }
            Ability copied = ability.copy();
            ability.getEffects().setValue("schemingFence", source.getSourceId());
            permanent.addAbility(ability, source.getSourceId(), game);
        }
        return true;
    }
}

class SchemingFenceManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    SchemingFenceManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may spend mana as though it were mana of any color to activate those abilities";
    }

    private SchemingFenceManaEffect(final SchemingFenceManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SchemingFenceManaEffect copy() {
        return new SchemingFenceManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        return source.isControlledBy(playerId)
                && affectedAbility
                .getEffects()
                .stream()
                .map(effect -> effect.getValue("schemingFence"))
                .filter(Objects::nonNull)
                .anyMatch(source.getSourceId()::equals);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
