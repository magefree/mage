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
package mage.sets.guildpact;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.abilities.keyword.HauntAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public class OrzhovPontiff extends CardImpl<OrzhovPontiff> {

    private static final FilterCreaturePermanent filterControlled = new FilterCreaturePermanent("Creatures you control");
    private static final FilterCreaturePermanent filterNotControlled = new FilterCreaturePermanent("creatures you don't control");
    static {
        filterControlled.add(new ControllerPredicate(TargetController.YOU));
        filterNotControlled.add((new ControllerPredicate(TargetController.NOT_YOU)));
    }

    public OrzhovPontiff(UUID ownerId) {
        super(ownerId, 124, "Orzhov Pontiff", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");
        this.expansionSetCode = "GPT";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haunt
        // When Orzhov Pontiff enters the battlefield or the creature it haunts dies, choose one - Creatures you control get +1/+1 until end of turn; or creatures you don't control get -1/-1 until end of turn.
        Ability ability = new HauntAbility(this, new BoostAllEffect(1,1, Duration.EndOfTurn, filterControlled, false));
        Mode mode = new Mode();
        mode.getEffects().add(new BoostAllEffect(-1,-1, Duration.EndOfTurn, filterNotControlled, false));
        ability.addMode(mode);
        this.addAbility(ability);

    }

    public OrzhovPontiff(final OrzhovPontiff card) {
        super(card);
    }

    @Override
    public OrzhovPontiff copy() {
        return new OrzhovPontiff(this);
    }
}
