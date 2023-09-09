package mage.cards.c;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.Card;
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

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CelestialDawn extends CardImpl {

    public CelestialDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // Lands you control are Plains.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CelestialDawnToPlainsEffect()));

        // Nonland cards you own that aren't on the battlefield, spells you control, and nonland permanents you control are white.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CelestialDawnToWhiteEffect()));

        // You may spend white mana as though it were mana of any color.
        // You may spend other mana only as though it were colorless mana.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new CelestialDawnSpendAnyManaEffect());
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
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Lands you control are Plains";
    }

    private CelestialDawnToPlainsEffect(final CelestialDawnToPlainsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public CelestialDawnToPlainsEffect copy() {
        return new CelestialDawnToPlainsEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent land : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    land.removeAllAbilities(source.getSourceId(), game);
                    land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                    break;
                case TypeChangingEffects_4:
                    land.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
                    land.addSubType(game, SubType.PLAINS);
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }

}

class CelestialDawnToWhiteEffect extends ContinuousEffectImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent();

    public CelestialDawnToWhiteEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        staticText = "Nonland permanents you control are white. The same is true for spells you control and nonland cards you own that aren't on the battlefield.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                setColor(perm.getColor(game), game);
            }
            // Stack
            for (MageObject object : game.getStack()) {
                if (object instanceof Spell && ((Spell) object).isControlledBy(controller.getId())) {
                    setColor(object.getColor(game), game);
                }
            }
            // Exile
            for (Card card : game.getExile().getAllCards(game)) {
                if (card.isOwnedBy(controller.getId())) {
                    setColor(card.getColor(game), game);
                }
            }
            // Command
            for (CommandObject commandObject : game.getState().getCommand()) {
                if (commandObject instanceof Commander) {
                    if (commandObject.isControlledBy(controller.getId())) {
                        setColor(commandObject.getColor(game), game);
                    }
                }
            }

            // Hand
            for (Card card : controller.getHand().getCards(game)) {
                setColor(card.getColor(game), game);
            }
            // Library
            for (Card card : controller.getLibrary().getCards(game)) {
                setColor(card.getColor(game), game);
            }
            // Graveyard
            for (Card card : controller.getGraveyard().getCards(game)) {
                setColor(card.getColor(game), game);
            }
            return true;
        }
        return false;
    }

    protected static void setColor(ObjectColor color, Game game) {
        color.setWhite(true);
        color.setGreen(false);
        color.setBlue(false);
        color.setBlack(false);
        color.setRed(false);
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
