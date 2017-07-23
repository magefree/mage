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
package mage.cards.o;

import java.util.Optional;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.AbilityAddingRemovingEffects_6;
import static mage.constants.Layer.PTChangingEffects_7;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class OverwhelmingSplendor extends CardImpl {

    public OverwhelmingSplendor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{W}{W}");

        this.subtype.add("Aura");
        this.subtype.add("Curse");

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.LoseAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Creatures enchanted player controls lose all abilities and have base power and toughness 1/1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OverwhelmingSplendorLoseAbilitiesEffect()));

        // Enchanted player can't activate abilities that aren't mana abilities or loyalty abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OverwhelmingSplendorCantActivateEffect()));
    }

    public OverwhelmingSplendor(final OverwhelmingSplendor card) {
        super(card);
    }

    @Override
    public OverwhelmingSplendor copy() {
        return new OverwhelmingSplendor(this);
    }
}

class OverwhelmingSplendorLoseAbilitiesEffect extends ContinuousEffectImpl {

    public OverwhelmingSplendorLoseAbilitiesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.LoseAbility);
        staticText = "Creatures enchanted player controls lose all abilities and have base power and toughness 1/1";
    }

    public OverwhelmingSplendorLoseAbilitiesEffect(final OverwhelmingSplendorLoseAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public OverwhelmingSplendorLoseAbilitiesEffect copy() {
        return new OverwhelmingSplendorLoseAbilitiesEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (enchantment == null) {
            return false;
        }
        Player player = game.getPlayer(enchantment.getAttachedTo());
        if (player != null) {
            for (Permanent permanent : game.getState().getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game)) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        permanent.removeAllAbilities(source.getSourceId(), game);
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getPower().setValue(1);
                            permanent.getToughness().setValue(1);
                        }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.PTChangingEffects_7;
    }

}

class OverwhelmingSplendorCantActivateEffect extends ContinuousRuleModifyingEffectImpl {

    public OverwhelmingSplendorCantActivateEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Enchanted player can't activate abilities that aren't mana abilities or loyalty abilities";
    }

    public OverwhelmingSplendorCantActivateEffect(final OverwhelmingSplendorCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public OverwhelmingSplendorCantActivateEffect copy() {
        return new OverwhelmingSplendorCantActivateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't activate abilities that aren't mana abilities or loyalty abilities (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
            Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (enchantment == null) {
                return false;
            }
            if (event.getPlayerId().equals(enchantment.getAttachedTo())) {
                Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
                if (ability.isPresent()
                        && !(ability.get() instanceof ActivatedManaAbilityImpl)
                        && !(ability.get() instanceof LoyaltyAbility)) {
                    return true;
                }
            }
        }
        return false;
    }
}
