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
package mage.sets.magic2010;

import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author North
 */
public class ConvincingMirage extends CardImpl<ConvincingMirage> {

    public ConvincingMirage(UUID ownerId) {
        super(ownerId, 46, "Convincing Mirage", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "M10";
        this.subtype.add("Aura");

        this.color.setBlue(true);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        // As Convincing Mirage enters the battlefield, choose a basic land type.
        this.addAbility(new AsEntersBattlefieldAbility(new ConvincingMirageEffect()));
        // Enchanted land is the chosen type.
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConvincingMirageContinousEffect()));
    }

    public ConvincingMirage(final ConvincingMirage card) {
        super(card);
    }

    @Override
    public ConvincingMirage copy() {
        return new ConvincingMirage(this);
    }
}

class ConvincingMirageEffect extends OneShotEffect<ConvincingMirageEffect> {

    public ConvincingMirageEffect() {
        super(Outcome.Neutral);
        this.staticText = "choose a basic land type";
    }

    public ConvincingMirageEffect(final ConvincingMirageEffect effect) {
        super(effect);
    }

    @Override
    public ConvincingMirageEffect copy() {
        return new ConvincingMirageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            ChoiceImpl choices = new ChoiceImpl(true);
            Set choicesSet = choices.getChoices();
            choicesSet.add("Forest");
            choicesSet.add("Plains");
            choicesSet.add("Mountain");
            choicesSet.add("Island");
            choicesSet.add("Swamp");
            if (player.choose(Outcome.Neutral, choices, game)) {
                game.getState().setValue(source.getSourceId().toString() + "_ConvincingMirage", choices.getChoice());
                return true;
            }
        }
        return false;
    }
}

class ConvincingMirageContinousEffect extends ContinuousEffectImpl<ConvincingMirageContinousEffect> {

    public ConvincingMirageContinousEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Enchanted land is the chosen type";
    }

    public ConvincingMirageContinousEffect(final ConvincingMirageContinousEffect effect) {
        super(effect);
    }

    @Override
    public ConvincingMirageContinousEffect copy() {
        return new ConvincingMirageContinousEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        String choice = (String) game.getState().getValue(source.getSourceId().toString() + "_ConvincingMirage");
        if (enchantment != null && enchantment.getAttachedTo() != null && choice != null) {
            Permanent land = game.getPermanent(enchantment.getAttachedTo());
            if (land != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            land.getSubtype().clear();
                            land.getSubtype().add(choice);
                        }
                        break;
                    case AbilityAddingRemovingEffects_6:
                        if (sublayer == SubLayer.NA) {
                            land.getAbilities().clear();
                            if (choice.equals("Forest")) {
                                land.addAbility(new GreenManaAbility(), game);
                            }
                            if (choice.equals("Plains")) {
                                land.addAbility(new WhiteManaAbility(), game);
                            }
                            if (choice.equals("Mountain")) {
                                land.addAbility(new RedManaAbility(), game);
                            }
                            if (choice.equals("Island")) {
                                land.addAbility(new BlueManaAbility(), game);
                            }
                            if (choice.equals("Swamp")) {
                                land.addAbility(new BlackManaAbility(), game);
                            }
                        }
                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}