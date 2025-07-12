package mage.cards.c;

import mage.MageItem;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.command.Commander;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.ManaPoolItem;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CelestialDawn extends CardImpl {

    public CelestialDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // Lands you control are Plains.
        this.addAbility(new SimpleStaticAbility(new CelestialDawnToPlainsEffect()));

        // Nonland cards you own that aren't on the battlefield, spells you control, and nonland permanents you control are white.
        this.addAbility(new SimpleStaticAbility(new CelestialDawnToWhiteEffect()));

        // You may spend white mana as though it were mana of any color.
        // You may spend other mana only as though it were colorless mana.
        Ability ability = new SimpleStaticAbility(new CelestialDawnSpendAnyManaEffect());
        ability.addEffect(new CelestialDawnSpendColorlessManaEffect());
        this.addAbility(ability);

    }

    private CelestialDawn(final CelestialDawn card) {
        super(card);
    }

    @Override
    public CelestialDawn copy() {
        return new CelestialDawn(this);
    }
}

class CelestialDawnToPlainsEffect extends ContinuousEffectImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    CelestialDawnToPlainsEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.staticText = "Lands you control are Plains";
    }

    private CelestialDawnToPlainsEffect(final CelestialDawnToPlainsEffect effect) {
        super(effect);
    }

    @Override
    public CelestialDawnToPlainsEffect copy() {
        return new CelestialDawnToPlainsEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent land = (Permanent) object;
            // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
            // So the ability removing has to be done before Layer 6
            // Lands have their mana ability intrinsically, so that is added in layer 4
            land.removeAllAbilities(source.getSourceId(), game);
            land.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
            land.addSubType(game, SubType.PLAINS);
            land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        affectedObjects.addAll(game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game));
        return !affectedObjects.isEmpty();
    }

}

class CelestialDawnToWhiteEffect extends ContinuousEffectImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent();

    public CelestialDawnToWhiteEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        staticText = "Nonland permanents you control are white. The same is true for spells you control and nonland cards you own that aren't on the battlefield.";
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            ((MageObject) object).getColor(game).setColor(ObjectColor.WHITE);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // Battlefield
        affectedObjects.addAll(game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game));
        // Stack
        for (MageObject object : game.getStack()) {
            if (object instanceof Spell && ((Spell) object).isControlledBy(controller.getId())) {
                affectedObjects.add(object);
            }
        }
        // Exile
        affectedObjects.addAll(game.getExile().getAllCards(game, controller.getId()));
        // Command
        for (CommandObject commandObject : game.getState().getCommand()) {
            if (commandObject instanceof Commander && commandObject.isControlledBy(controller.getId())) {
                affectedObjects.add(commandObject);
            }
        }
        // Hand
        affectedObjects.addAll(controller.getHand().getCards(game));
        // Library
        affectedObjects.addAll(controller.getLibrary().getCards(game));
        // Graveyard
        affectedObjects.addAll(controller.getGraveyard().getCards(game));

        return !affectedObjects.isEmpty();
    }

    @Override
    public CelestialDawnToWhiteEffect copy() {
        return new CelestialDawnToWhiteEffect(this);
    }

    private CelestialDawnToWhiteEffect(CelestialDawnToWhiteEffect effect) {
        super(effect);
    }

}

class CelestialDawnSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public CelestialDawnSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "You may spend white mana as though it were mana of any color";
    }

    private CelestialDawnSpendAnyManaEffect(final CelestialDawnSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CelestialDawnSpendAnyManaEffect copy() {
        return new CelestialDawnSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        if (mana.getWhite() > 0) {
            return ManaType.WHITE;
        }
        return null;
    }
}

class CelestialDawnSpendColorlessManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public CelestialDawnSpendColorlessManaEffect() {
        super(AsThoughEffectType.SPEND_ONLY_MANA, Duration.Custom, Outcome.Detriment);
        staticText = "You may spend other mana only as though it were colorless mana";
    }

    private CelestialDawnSpendColorlessManaEffect(final CelestialDawnSpendColorlessManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CelestialDawnSpendColorlessManaEffect copy() {
        return new CelestialDawnSpendColorlessManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        if (mana.getWhite() == 0) {
            return ManaType.COLORLESS;
        } else {
            // must return manaType cause applied all the time
            return manaType;
        }
    }
}
