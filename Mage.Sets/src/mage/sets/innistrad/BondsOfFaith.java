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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author nantuko
 */
public class BondsOfFaith extends CardImpl<BondsOfFaith> {

    private static final String rule = "Enchanted creature gets +2/+2 as long as it's a Human";

    public BondsOfFaith(UUID ownerId) {
        super(ownerId, 5, "Bonds of Faith", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Aura");

        this.color.setWhite(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.Neutral));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 as long as it's a Human. Otherwise, it can't attack or block.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ConditionalContinousEffect(new BoostEquippedEffect(2, 2), new EquippedHasSubtypeCondition("Human"), rule)));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BondsOfFaithEffect()));
    }

    public BondsOfFaith(final BondsOfFaith card) {
        super(card);
    }

    @Override
    public BondsOfFaith copy() {
        return new BondsOfFaith(this);
    }
}

class BondsOfFaithEffect extends RestrictionEffect<BondsOfFaithEffect> {

    private static final Condition condition = new EquippedHasSubtypeCondition("Human");

    public BondsOfFaithEffect() {
        super(Constants.Duration.WhileOnBattlefield);
        staticText = "Otherwise, it can't attack or block";
    }

    public BondsOfFaithEffect(final BondsOfFaithEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getAttachments().contains((source.getSourceId()))) {
            if (!condition.apply(game, source)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public BondsOfFaithEffect copy() {
        return new BondsOfFaithEffect(this);
    }

}
