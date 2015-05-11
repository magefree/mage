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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author anonymous
 */
public class InstillEnergy extends CardImpl {

    public InstillEnergy(UUID ownerId) {
        super(ownerId, 166, "Instill Energy", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.expansionSetCode = "5ED";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted creature can attack as though it had haste.
        Ability asThough = new SimpleStaticAbility(Zone.BATTLEFIELD, new CanAttackAsThoughItHadHasteEnchantedEffect(Duration.WhileOnBattlefield));
        Ability haste = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(asThough, AttachmentType.AURA, Duration.WhileOnBattlefield, "Enchanted creature can attack as though it had haste."));
        this.addAbility(haste);
        // {0}: Untap enchanted creature. Activate this ability only during your turn and only once each turn.
        Ability gainedAbility = new LimitedTimesIfConditionActivatedAbility(Zone.BATTLEFIELD, new UntapEnchantedEffect(), new GenericManaCost(0), MyTurnCondition.getInstance(), 1);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA, Duration.WhileOnBattlefield, "{0}: Untap enchanted creature. Activate this ability only during your turn and only once each turn.")));
    }

    public InstillEnergy(final InstillEnergy card) {
        super(card);
    }

    @Override
    public InstillEnergy copy() {
        return new InstillEnergy(this);
    }
}

class CanAttackAsThoughItHadHasteEnchantedEffect extends AsThoughEffectImpl {
    public CanAttackAsThoughItHadHasteEnchantedEffect(Duration duration) {
        super(AsThoughEffectType.ATTACK, duration, Outcome.Benefit);
        staticText = "Enchanted creature can attack as though it had haste";
    }

    public CanAttackAsThoughItHadHasteEnchantedEffect(final CanAttackAsThoughItHadHasteEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CanAttackAsThoughItHadHasteEnchantedEffect copy() {
        return new CanAttackAsThoughItHadHasteEnchantedEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        return enchantment != null && enchantment.getAttachedTo() != null && enchantment.getAttachedTo() == objectId;
    }
}

class LimitedTimesIfConditionActivatedAbility extends ActivateIfConditionActivatedAbility {

    class ActivationInfo {

        public int turnNum;
        public int activationCounter;

        public ActivationInfo(int turnNum, int activationCounter) {
            this.turnNum = turnNum;
            this.activationCounter = activationCounter;
        }
    }

    private int maxActivationsPerTurn;

    public LimitedTimesIfConditionActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition) {
        this(zone, effect, cost, condition, 1);
    }

    public LimitedTimesIfConditionActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition, int maxActivationsPerTurn) {
        super(zone, effect, cost, condition);
        this.maxActivationsPerTurn = maxActivationsPerTurn;
    }

    public LimitedTimesIfConditionActivatedAbility(LimitedTimesIfConditionActivatedAbility ability) {
        super(ability);
        this.maxActivationsPerTurn = ability.maxActivationsPerTurn;
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game)) {
            ActivationInfo activationInfo = getActivationInfo(game);
            return activationInfo == null || activationInfo.turnNum != game.getTurnNum() || activationInfo.activationCounter < maxActivationsPerTurn;
        }
        return false;
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (canActivate(this.controllerId, game)) {
            if (super.activate(game, noMana)) {
                ActivationInfo activationInfo = getActivationInfo(game);
                if (activationInfo == null) {
                    activationInfo = new ActivationInfo(game.getTurnNum(), 1);
                } else if (activationInfo.turnNum != game.getTurnNum()) {
                    activationInfo.turnNum = game.getTurnNum();
                    activationInfo.activationCounter = 1;
                } else {
                    activationInfo.activationCounter++;
                }
                setActivationInfo(activationInfo, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean resolve(Game game) {
        return super.resolve(game);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder(super.getRule());
        sb.replace(sb.length() - 1, sb.length() - 1, " and "); //suppress super()'s final period
        switch (maxActivationsPerTurn) {
            case 1:
                sb.append("only once");
                break;
            case 2:
                sb.append("no more than twice");
                break;
            default:
                sb.append("no more than ").append(CardUtil.numberToText(maxActivationsPerTurn)).append(" times");
        }
        sb.append(" each turn.");
        return sb.toString();
    }

    @Override
    public LimitedTimesIfConditionActivatedAbility copy() {
        return new LimitedTimesIfConditionActivatedAbility(this);
    }

    private ActivationInfo getActivationInfo(Game game) {
        Integer turnNum = (Integer) game.getState().getValue(CardUtil.getCardZoneString("activationsTurn", sourceId, game));
        Integer activationCount = (Integer) game.getState().getValue(CardUtil.getCardZoneString("activationsCount", sourceId, game));
        if (turnNum == null || activationCount == null) {
            return null;
        }
        return new ActivationInfo(turnNum, activationCount);
    }

    private void setActivationInfo(ActivationInfo activationInfo, Game game) {
        game.getState().setValue(CardUtil.getCardZoneString("activationsTurn", sourceId, game), activationInfo.turnNum);
        game.getState().setValue(CardUtil.getCardZoneString("activationsCount", sourceId, game), activationInfo.activationCounter);
    }
}
