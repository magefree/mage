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
package mage.sets.theros;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public class NyleasPresence extends CardImpl<NyleasPresence> {

    public NyleasPresence(UUID ownerId) {
        super(ownerId, 169, "Nylea's Presence", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "THS";
        this.subtype.add("Aura");

        this.color.setGreen(true);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // When Nylea's Presence enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardControllerEffect(1)));
        // Enchanted land is every basic land type in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NyleasPresenceLandTypeEffect("Swamp", "Mountain", "Forest", "Island", "Plains")));

    }

    public NyleasPresence(final NyleasPresence card) {
        super(card);
    }

    @Override
    public NyleasPresence copy() {
        return new NyleasPresence(this);
    }
}

class NyleasPresenceLandTypeEffect extends ContinuousEffectImpl<NyleasPresenceLandTypeEffect> {

    protected ArrayList<String> landTypes = new ArrayList();

    public NyleasPresenceLandTypeEffect(String... landNames) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        landTypes.addAll(Arrays.asList(landNames));
        this.staticText = "Enchanted land is every basic land type in addition to its other types";
    }

    public NyleasPresenceLandTypeEffect(final NyleasPresenceLandTypeEffect effect) {
        super(effect);
        this.landTypes.addAll(effect.landTypes);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public NyleasPresenceLandTypeEffect copy() {
        return new NyleasPresenceLandTypeEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent land = game.getPermanent(enchantment.getAttachedTo());
            if (land != null) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        Mana mana = new Mana();
                        for (Ability ability : land.getAbilities()){
                            if (ability instanceof BasicManaAbility) {
                                mana.add(((BasicManaAbility)ability ).getNetMana(game));
                            }
                        }
                        if (mana.getGreen() == 0 && landTypes.contains("Forest")) {
                            land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                        }
                        if (mana.getRed() == 0 && landTypes.contains("Mountain")) {
                            land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                        }
                        if (mana.getBlue() == 0 && landTypes.contains("Island")) {
                            land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                        }
                        if (mana.getWhite() == 0 && landTypes.contains("Plains")) {
                            land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                        }
                        if (mana.getBlack() == 0 && landTypes.contains("Swamp")) {
                            land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                        }
                        break;
                    case TypeChangingEffects_4:
                        for (String subtype : landTypes) {
                            if (!land.getSubtype().contains(subtype)) {
                                land.getSubtype().add(subtype);
                            }
                        }
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
