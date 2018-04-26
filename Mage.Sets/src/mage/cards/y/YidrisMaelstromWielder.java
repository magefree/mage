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
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class YidrisMaelstromWielder extends CardImpl {

    public YidrisMaelstromWielder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{R}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Yidris, Maelstrom Wielder deals combat damage to a player, as you cast spells from your hand this turn, they gain cascade.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new YidrisMaelstromWielderGainCascadeEffect(), false));
    }

    public YidrisMaelstromWielder(final YidrisMaelstromWielder card) {
        super(card);
    }

    @Override
    public YidrisMaelstromWielder copy() {
        return new YidrisMaelstromWielder(this);
    }
}

class YidrisMaelstromWielderGainCascadeEffect extends ContinuousEffectImpl {

    private final Ability cascadeAbility = new CascadeAbility();

    public YidrisMaelstromWielderGainCascadeEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "as you cast spells from your hand this turn, they gain cascade";
    }

    public YidrisMaelstromWielderGainCascadeEffect(final YidrisMaelstromWielderGainCascadeEffect effect) {
        super(effect);
    }

    @Override
    public YidrisMaelstromWielderGainCascadeEffect copy() {
        return new YidrisMaelstromWielderGainCascadeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (StackObject stackObject : game.getStack()) {
                // only spells cast, so no copies of spells
                if ((stackObject instanceof Spell) && !stackObject.isCopy() && stackObject.getControllerId().equals(source.getControllerId())) {
                    Spell spell = (Spell) stackObject;
                    if (spell.getFromZone() == Zone.HAND) {
                        game.getState().addOtherAbility(spell.getCard(), cascadeAbility);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
