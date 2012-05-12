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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author noxx
 */
public class HuntedGhoul extends CardImpl<HuntedGhoul> {

    public HuntedGhoul(UUID ownerId) {
        super(ownerId, 110, "Hunted Ghoul", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Zombie");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Hunted Ghoul can't block Humans.
        this.addAbility(HuntedGhoulAbility.getInstance());
    }

    public HuntedGhoul(final HuntedGhoul card) {
        super(card);
    }

    @Override
    public HuntedGhoul copy() {
        return new HuntedGhoul(this);
    }
}

class HuntedGhoulAbility extends EvasionAbility<HuntedGhoulAbility> {

    private static HuntedGhoulAbility instance;

    public static HuntedGhoulAbility getInstance() {
        if (instance == null) {
            instance = new HuntedGhoulAbility();
        }
        return instance;
    }

    private HuntedGhoulAbility() {
        this.addEffect(new HuntedGhoulEffect());
    }

    @Override
    public String getRule() {
        return "{this} can't block Humans.";
    }

    @Override
    public HuntedGhoulAbility copy() {
        return getInstance();
    }
}

class HuntedGhoulEffect extends RestrictionEffect<HuntedGhoulEffect> {

    public HuntedGhoulEffect() {
        super(Constants.Duration.WhileOnBattlefield);
    }

    public HuntedGhoulEffect(final HuntedGhoulEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getAbilities().containsKey(HuntedGhoulAbility.getInstance().getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (attacker.getSubtype().contains("Human")) {
            return false;
        }
        return true;
    }

    @Override
    public HuntedGhoulEffect copy() {
        return new HuntedGhoulEffect(this);
    }
}
