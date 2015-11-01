/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.timeshifted;

import java.util.UUID;
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
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.AbilityAddingRemovingEffects_6;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.sets.Commander;

/**
 *
 * @author LevelX2
 */
public class CelestialDawn extends CardImpl {

    public CelestialDawn(UUID ownerId) {
        super(ownerId, 3, "Celestial Dawn", Rarity.SPECIAL, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");
        this.expansionSetCode = "TSB";

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

    public CelestialDawn(final CelestialDawn card) {
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

    CelestialDawnToPlainsEffect(final CelestialDawnToPlainsEffect effect) {
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
                    land.getSubtype().clear();
                    land.getSubtype().add("Plains");
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
        staticText = "Nonland cards you own that aren't on the battlefield, spells you control, and nonland permanents you control are white";
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
                if (object instanceof Spell && ((Spell) object).getControllerId().equals(controller.getId())) {
                    setColor(object.getColor(game), game);
                }
            }
            // Exile
            for (Card card : game.getExile().getAllCards(game)) {
                if (card.getOwnerId().equals(controller.getId())) {
                    setColor(card.getColor(game), game);
                }
            }
            // Command
            for (CommandObject commandObject : game.getState().getCommand()) {
                if (commandObject instanceof Commander) {
                    if (commandObject.getControllerId().equals(controller.getId())) {
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

    public CelestialDawnSpendAnyManaEffect(final CelestialDawnSpendAnyManaEffect effect) {
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
        return affectedControllerId.equals(source.getControllerId());
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

    public CelestialDawnSpendColorlessManaEffect(final CelestialDawnSpendColorlessManaEffect effect) {
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
        return affectedControllerId.equals(source.getControllerId());
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        if (mana.getWhite() == 0 && !ManaType.COLORLESS.equals(manaType)) {
            return null;
        }
        return manaType;
    }
}
