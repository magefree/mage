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
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class ConquerorsFlail extends CardImpl {

    public ConquerorsFlail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each color among permanents you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(new ConquerorsFlailColorCount(), new ConquerorsFlailColorCount(), Duration.WhileOnBattlefield)));

        // As long as Conqueror's Flail is attached to a creature, your opponents can't cast spells during your turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConquerorsFlailEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    public ConquerorsFlail(final ConquerorsFlail card) {
        super(card);
    }

    @Override
    public ConquerorsFlail copy() {
        return new ConquerorsFlail(this);
    }
}

class ConquerorsFlailColorCount implements DynamicValue {

    public ConquerorsFlailColorCount() {
    }

    public ConquerorsFlailColorCount(final ConquerorsFlailColorCount dynamicValue) {
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        Player controller = game.getPlayer(source.getControllerId());
        int count = 0;
        if (controller != null) {
            Mana mana = new Mana();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controller.getId())) {
                if (mana.getBlack() == 0 && permanent.getColor(game).isBlack()) {
                    mana.increaseBlack();
                    count++;
                }
                if (mana.getBlue() == 0 && permanent.getColor(game).isBlue()) {
                    mana.increaseBlue();
                    count++;
                }
                if (mana.getRed() == 0 && permanent.getColor(game).isRed()) {
                    mana.increaseRed();
                    count++;
                }
                if (mana.getGreen() == 0 && permanent.getColor(game).isGreen()) {
                    mana.increaseGreen();
                    count++;
                }
                if (mana.getWhite() == 0 && permanent.getColor(game).isWhite()) {
                    mana.increaseWhite();
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "for each color among permanents you control";
    }

    @Override
    public DynamicValue copy() {
        return new ConquerorsFlailColorCount();
    }
}

class ConquerorsFlailEffect extends ContinuousRuleModifyingEffectImpl {

    public ConquerorsFlailEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as {this} is attached to a creature, your opponents can't cast spells during your turn";
    }

    public ConquerorsFlailEffect(final ConquerorsFlailEffect effect) {
        super(effect);
    }

    @Override
    public ConquerorsFlailEffect copy() {
        return new ConquerorsFlailEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        boolean isAttached = false;
        if (permanent != null) {
            UUID attachedTo = permanent.getAttachedTo();
            Permanent attachment = game.getPermanent(attachedTo);
            if (attachment != null) {
                isAttached = true;
            }
        }

        if (isAttached && game.getActivePlayerId().equals(source.getControllerId())
                && game.getPlayer(source.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            return true;
        }
        return false;
    }
}
