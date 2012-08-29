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
package mage.sets.riseoftheeldrazi;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public class WarmongersChariot extends CardImpl<WarmongersChariot> {

    private static final String staticText = "As long as equipped creature has defender, it can attack as though it didn't have defender";

    public WarmongersChariot(UUID ownerId) {
        super(ownerId, 226, "Warmonger's Chariot", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Equipment");

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));

        // As long as equipped creature has defender, it can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new WarmongersChariotEffect()));

        // Equip {3}
        this.addAbility(new EquipAbility(Constants.Outcome.AddAbility, new GenericManaCost(3)));
    }

    public WarmongersChariot(final WarmongersChariot card) {
        super(card);
    }

    @Override
    public WarmongersChariot copy() {
        return new WarmongersChariot(this);
    }
}

class WarmongersChariotEffect extends AsThoughEffectImpl<WarmongersChariotEffect> {

    public WarmongersChariotEffect() {
        super(Constants.AsThoughEffectType.ATTACK, Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = "As long as equipped creature has defender, it can attack as though it didn't have defender";
    }

    public WarmongersChariotEffect(final WarmongersChariotEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public WarmongersChariotEffect copy() {
        return new WarmongersChariotEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(equipment.getAttachedTo());
            if (creature != null && creature.getId().equals(sourceId)
                    && creature.getAbilities().containsKey(DefenderAbility.getInstance().getId())) {
                return true;
            }
        }
        return false;
    }

}
