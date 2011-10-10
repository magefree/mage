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
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class OrchardSpirit extends CardImpl<OrchardSpirit> {

    public OrchardSpirit(UUID ownerId) {
        super(ownerId, 198, "Orchard Spirit", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Spirit");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Orchard Spirit can't be blocked except by creatures with flying or reach.
        this.addAbility(OrchardSpiritAbility.getInstance());

    }

    public OrchardSpirit(final OrchardSpirit card) {
        super(card);
    }

    @Override
    public OrchardSpirit copy() {
        return new OrchardSpirit(this);
    }
}

class OrchardSpiritAbility extends EvasionAbility<OrchardSpiritAbility> {

    private static OrchardSpiritAbility instance;

    public static OrchardSpiritAbility getInstance() {
        if (instance == null) {
            instance = new OrchardSpiritAbility();
        }
        return instance;
    }

    private OrchardSpiritAbility() {
        this.addEffect(new OrchardSpiritEffect());
    }

    @Override
    public String getRule() {
        return "{this} can't be blocked except by creatures with flying or reach.";
    }

    @Override
    public OrchardSpiritAbility copy() {
        return getInstance();
    }
}

class OrchardSpiritEffect extends RestrictionEffect<OrchardSpiritEffect> {

    public OrchardSpiritEffect() {
        super(Duration.WhileOnBattlefield);
    }

    public OrchardSpiritEffect(final OrchardSpiritEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getAbilities().containsKey(OrchardSpiritAbility.getInstance().getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (blocker.getAbilities().contains(FlyingAbility.getInstance()) || blocker.getAbilities().contains(ReachAbility.getInstance())) {
            return true;
        }
        return false;
    }

    @Override
    public OrchardSpiritEffect copy() {
        return new OrchardSpiritEffect(this);
    }
}