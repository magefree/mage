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
package mage.sets.commander2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantAttackControllerAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class AssaultSuit extends CardImpl {

    public AssaultSuit(UUID ownerId) {
        super(ownerId, 53, "Assault Suit", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "C14";
        this.subtype.add("Equipment");

        // Equipped creature gets +2/+2, has haste, can't attack you or a planeswalker you control, and can't be sacrificed.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2));
        Effect effect = new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText(", has haste");
        ability.addEffect(effect);
        effect = new CantAttackControllerAttachedEffect(AttachmentType.EQUIPMENT);
        effect.setText(", can't attack you or a planeswalker you control");
        ability.addEffect(effect);
        effect = new AssaultSuitCantBeSacrificed();
        effect.setText(", and can't be sacrificed");
        ability.addEffect(effect);
        this.addAbility(ability);

        // At the beginning of each opponent's upkeep, you may have that player gain control of equipped creature until end of turn. If you do, untap it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AssaultSuitGainControlEffect(), TargetController.OPPONENT, false));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.Detriment, new GenericManaCost(3)));
    }

    public AssaultSuit(final AssaultSuit card) {
        super(card);
    }

    @Override
    public AssaultSuit copy() {
        return new AssaultSuit(this);
    }
}

 class AssaultSuitCantBeSacrificed extends ContinuousRuleModifyingEffectImpl {

    public AssaultSuitCantBeSacrificed() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, true, false);
        staticText = "and can't be sacrificed";
    }

    public AssaultSuitCantBeSacrificed(final AssaultSuitCantBeSacrificed effect) {
        super(effect);
    }

    @Override
    public AssaultSuitCantBeSacrificed copy() {
        return new AssaultSuitCantBeSacrificed(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        return "This creature can't be sacrificed.";
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.SACRIFICE_PERMANENT) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                return equipment.getAttachedTo().equals(event.getTargetId());
            }
        }
        return false;
    }
}

class AssaultSuitGainControlEffect extends OneShotEffect {

    public AssaultSuitGainControlEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may have that player gain control of equipped creature until end of turn. If you do, untap it";
    }

    public AssaultSuitGainControlEffect(final AssaultSuitGainControlEffect effect) {
        super(effect);
    }

    @Override
    public AssaultSuitGainControlEffect copy() {
        return new AssaultSuitGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (controller != null && activePlayer != null && equipment != null) {
            if (equipment.getAttachedTo() != null) {
                Permanent equippedCreature = game.getPermanent(equipment.getAttachedTo());
                if (equippedCreature != null && controller.chooseUse(outcome,
                        "Let have " + activePlayer.getLogName() + " gain control of " + equippedCreature.getLogName() + "?", source, game)) {
                    equippedCreature.untap(game);
                    ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn, activePlayer.getId());
                    effect.setTargetPointer(new FixedTarget(equipment.getAttachedTo()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }


        return false;
    }
}
