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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamageCreatureEvent;
import mage.game.events.DamagePlaneswalkerEvent;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import static mage.game.events.GameEvent.EventType.DAMAGE_PLANESWALKER;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class DoranTheSiegeTower extends CardImpl<DoranTheSiegeTower> {

    public DoranTheSiegeTower(UUID ownerId) {
        super(ownerId, 247, "Doran, the Siege Tower", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}{G}{W}");
        this.expansionSetCode = "LRW";
        this.supertype.add("Legendary");
        this.subtype.add("Treefolk");
        this.subtype.add("Shaman");

        this.color.setGreen(true);
        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Each creature assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DoranTheSiegeTowerEffect()));
    }

    public DoranTheSiegeTower(final DoranTheSiegeTower card) {
        super(card);
    }

    @Override
    public DoranTheSiegeTower copy() {
        return new DoranTheSiegeTower(this);
    }
}

class DoranTheSiegeTowerEffect extends ReplacementEffectImpl<DoranTheSiegeTowerEffect> {

    public DoranTheSiegeTowerEffect() {
        super(Constants.Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "Each creature assigns combat damage equal to its toughness rather than its power";
    }

    public DoranTheSiegeTowerEffect(final DoranTheSiegeTowerEffect effect) {
        super(effect);
    }

    @Override
    public DoranTheSiegeTowerEffect copy() {
        return new DoranTheSiegeTowerEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
                 if (((DamagePlayerEvent) event).isCombatDamage() &&
                    (event.getAppliedEffects() == null || !event.getAppliedEffects().contains(this.getId()))) {
                    event.getAppliedEffects().add(this.getId());
                    return true;
                 }
                 break;
            case DAMAGE_PLANESWALKER:
                 if (((DamagePlaneswalkerEvent) event).isCombatDamage() &&
                    (event.getAppliedEffects() == null || !event.getAppliedEffects().contains(this.getId()))) {
                    event.getAppliedEffects().add(this.getId());
                    return true;
                 }
                 break;
            case DAMAGE_CREATURE:
                 if (((DamageCreatureEvent) event).isCombatDamage() &&
                    (event.getAppliedEffects() == null || !event.getAppliedEffects().contains(this.getId()))) {
                    event.getAppliedEffects().add(this.getId());
                    return true;
                 }
                 break;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
         Permanent permanent = game.getPermanent(event.getSourceId());
         if (permanent != null) {
             event.setAmount(permanent.getToughness().getValue());
         }
         return false;
    }

}
