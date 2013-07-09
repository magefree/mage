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
package mage.sets.magic2014;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;

/**
 *
 * @author jeffwadsworth
 */
public class FiendslayerPaladin extends CardImpl<FiendslayerPaladin> {

    public FiendslayerPaladin(UUID ownerId) {
        super(ownerId, 18, "Fiendslayer Paladin", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "M14";
        this.subtype.add("Human");
        this.subtype.add("Knight");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Fiendslayer Paladin can't be the target of black or red spells your opponents control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FiendslayerPaladinEffect()));
        
    }

    public FiendslayerPaladin(final FiendslayerPaladin card) {
        super(card);
    }

    @Override
    public FiendslayerPaladin copy() {
        return new FiendslayerPaladin(this);
    }
}

class FiendslayerPaladinEffect extends ReplacementEffectImpl<FiendslayerPaladinEffect> {

    public FiendslayerPaladinEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "{this} can't be the target of black or red spells your opponents control";
    }

    public FiendslayerPaladinEffect(final FiendslayerPaladinEffect effect) {
        super(effect);
    }

    @Override
    public FiendslayerPaladinEffect copy() {
        return new FiendslayerPaladinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.TARGET) {
            Card targetCard = game.getCard(event.getTargetId());
            StackObject stackObject = (StackObject) game.getStack().getStackObject(event.getSourceId());
            if (targetCard != null && stackObject != null) {
                if (stackObject.getColor().contains(ObjectColor.BLACK)
                        || stackObject.getColor().contains(ObjectColor.RED)) {
                    if (!stackObject.getControllerId().equals(source.getControllerId())
                            && stackObject.getCardType().contains(CardType.INSTANT)
                            || stackObject.getCardType().contains(CardType.SORCERY)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
