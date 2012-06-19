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
package mage.sets.magic2012;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.BloodthirstAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 * @author nantuko
 */
public class BloodlordOfVaasgoth extends CardImpl<BloodlordOfVaasgoth> {

    private static final FilterSpell filter = new FilterSpell("a Vampire creature spell");

    static {
        filter.getCardType().add(CardType.CREATURE);
        filter.setScopeCardType(Filter.ComparisonScope.Any);
        filter.getSubtype().add("Vampire");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public BloodlordOfVaasgoth(UUID ownerId) {
        super(ownerId, 82, "Bloodlord of Vaasgoth", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "M12";
        this.subtype.add("Vampire");
        this.subtype.add("Warrior");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bloodthirst 3
        this.addAbility(new BloodthirstAbility(3));

        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a Vampire creature spell, it gains bloodthirst 3.
        this.addAbility(new SpellCastTriggeredAbility(new BloodlordOfVaasgothEffect(), filter, false, true));
    }

    public BloodlordOfVaasgoth(final BloodlordOfVaasgoth card) {
        super(card);
    }

    @Override
    public BloodlordOfVaasgoth copy() {
        return new BloodlordOfVaasgoth(this);
    }
}

class BloodlordOfVaasgothEffect extends ContinuousEffectImpl {

    private Ability ability = new BloodthirstAbility(3);

    public BloodlordOfVaasgothEffect() {
        super(Constants.Duration.OneUse, Constants.Layer.AbilityAddingRemovingEffects_6, Constants.SubLayer.NA, Constants.Outcome.AddAbility);
        staticText = "it gains bloodthirst 3";
    }

    public BloodlordOfVaasgothEffect(final BloodlordOfVaasgothEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public BloodlordOfVaasgothEffect copy() {
        return new BloodlordOfVaasgothEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell object = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (object != null) {
            Permanent permanent = game.getPermanent(object.getSourceId());
            if (permanent != null) {
                permanent.addAbility(ability, game);
                return true;
            }
        } else {
            used = true;
        }
        return false;
    }

}
