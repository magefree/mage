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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Loki
 */
public class ContaminatedGround extends CardImpl<ContaminatedGround> {

    public ContaminatedGround(UUID ownerId) {
        super(ownerId, 102, "Contaminated Ground", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Aura");

        this.color.setBlack(true);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted land is a Swamp.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ContaminatedGroundEffect()));
        // Whenever enchanted land becomes tapped, its controller loses 2 life.
        this.addAbility(new ContaminatedGroundAbility());
    }

    public ContaminatedGround(final ContaminatedGround card) {
        super(card);
    }

    @Override
    public ContaminatedGround copy() {
        return new ContaminatedGround(this);
    }
}

class ContaminatedGroundAbility extends TriggeredAbilityImpl<ContaminatedGroundAbility> {
    ContaminatedGroundAbility() {
        super(Constants.Zone.BATTLEFIELD, new LoseLifeTargetEffect(2));
    }

    ContaminatedGroundAbility(final ContaminatedGroundAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED) {
            Permanent source = game.getPermanent(this.sourceId);
            if (source != null && source.getAttachedTo().equals(event.getTargetId())) {
                Permanent attached = game.getPermanent(source.getAttachedTo());
                if (attached != null) {

                    for (Effect e : getEffects()) {
                        e.setTargetPointer(new FixedTarget(attached.getControllerId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ContaminatedGroundAbility copy() {
        return new ContaminatedGroundAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever enchanted land becomes tapped, its controller loses 2 life.";
    }
}

class ContaminatedGroundEffect extends ContinuousEffectImpl<ContaminatedGroundEffect> {
    ContaminatedGroundEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Detriment);
        this.staticText = "Enchanted land is a Swamp";
    }

    ContaminatedGroundEffect(final ContaminatedGroundEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public ContaminatedGroundEffect copy() {
        return new ContaminatedGroundEffect(this);
    }

    public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent land = game.getPermanent(enchantment.getAttachedTo());
            if (land != null) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        land.getAbilities().clear();
                        land.addAbility(new BlackManaAbility(), game);
                        break;
                    case ControlChangingEffects_2:
                        land.getColor().setColor(new ObjectColor(""));
                        break;
                    case TypeChangingEffects_4:
                        if (!land.getCardType().contains(CardType.LAND))
                            land.getCardType().add(CardType.LAND);

                        land.getSubtype().clear();
                        land.getSubtype().add("Swamp");

                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasLayer(Constants.Layer layer) {
        return layer == Constants.Layer.AbilityAddingRemovingEffects_6 || layer == Constants.Layer.ColorChangingEffects_5 || layer == Constants.Layer.TypeChangingEffects_4;
    }
}