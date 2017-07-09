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
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.BloodthirstAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author nantuko
 */
public class BloodlordOfVaasgoth extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Vampire creature spell");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new SubtypePredicate(SubType.VAMPIRE));
    }

    public BloodlordOfVaasgoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add("Vampire");
        this.subtype.add("Warrior");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bloodthirst 3
        this.addAbility(new BloodthirstAbility(3));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a Vampire creature spell, it gains bloodthirst 3.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BloodlordOfVaasgothEffect(), filter, false, true));
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
    private int zoneChangeCounter;
    private UUID permanentId;

    public BloodlordOfVaasgothEffect() {
        super(Duration.OneUse, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "it gains bloodthirst 3";
    }

    public BloodlordOfVaasgothEffect(final BloodlordOfVaasgothEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.zoneChangeCounter = effect.zoneChangeCounter;
        this.permanentId = effect.permanentId;
    }

    @Override
    public BloodlordOfVaasgothEffect copy() {
        return new BloodlordOfVaasgothEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Spell object = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (object != null) {
            zoneChangeCounter = game.getState().getZoneChangeCounter(object.getSourceId()) + 1;
            permanentId = object.getSourceId();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(permanentId);
        if (permanent != null && permanent.getZoneChangeCounter(game) <= zoneChangeCounter) {
            permanent.addAbility(ability, source.getSourceId(), game);
            return true;
        } else {
            if (game.getState().getZoneChangeCounter(permanentId) >= zoneChangeCounter) {
                discard();
            }
            Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
            if (spell != null) { // Bloodthirst checked while spell is on the stack so needed to give it already to the spell
                game.getState().addOtherAbility(spell.getCard(), ability, true);
            }
        }
        return true;
    }

}
