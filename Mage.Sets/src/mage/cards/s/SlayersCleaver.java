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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class SlayersCleaver extends CardImpl {

    public SlayersCleaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add("Equipment");

        // Equipped creature gets +3/+1 and must be blocked by an Eldrazi if able.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 1));
        ability.addEffect(new SlayersCleaverEffect());
        this.addAbility(ability);
        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{4}")));
    }

    public SlayersCleaver(final SlayersCleaver card) {
        super(card);
    }

    @Override
    public SlayersCleaver copy() {
        return new SlayersCleaver(this);
    }
}

class SlayersCleaverEffect extends RequirementEffect {

    public SlayersCleaverEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public SlayersCleaverEffect(Duration duration) {
        super(duration);
        staticText = "and must be blocked by an Eldrazi if able";
    }

    public SlayersCleaverEffect(final SlayersCleaverEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.canBlock(source.getSourceId(), game) && permanent.hasSubtype(SubType.ELDRAZI, game);
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public UUID mustBlockAttackerIfElseUnblocked(Ability source, Game game) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null) {
            return equipment.getAttachedTo();
        }
        return null;
    }

    @Override
    public SlayersCleaverEffect copy() {
        return new SlayersCleaverEffect(this);
    }

}
