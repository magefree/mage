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

import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.LevelerCard;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author North, noxx
 */
public class ZulaportEnforcer extends LevelerCard<ZulaportEnforcer> {

    public ZulaportEnforcer(UUID ownerId) {
        super(ownerId, 133, "Zulaport Enforcer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl("{4}")));

        // LEVEL 1-2:  3/3

        // LEVEL 3+: 5/5
        // Zulaport Enforcer can't be blocked except by black creatures.
        Abilities<Ability> levelAbilities = new AbilitiesImpl<Ability>();
        levelAbilities.add(ZulaportEnforcerAbility.getInstance());

        LevelerCardBuilder.construct(this,
                new LevelerCardBuilder.LevelAbility(1, 2, new AbilitiesImpl<Ability>(), 3, 3),
                new LevelerCardBuilder.LevelAbility(3, -1, levelAbilities, 5, 5)
        );
    }

    public ZulaportEnforcer(final ZulaportEnforcer card) {
        super(card);
    }

    @Override
    public ZulaportEnforcer copy() {
        return new ZulaportEnforcer(this);
    }
}

class ZulaportEnforcerAbility extends EvasionAbility<ZulaportEnforcerAbility> {

    private static ZulaportEnforcerAbility instance;

    public static ZulaportEnforcerAbility getInstance() {
        if (instance == null) {
            instance = new ZulaportEnforcerAbility();
        }
        return instance;
    }

    private ZulaportEnforcerAbility() {
        this.addEffect(new ZulaportEnforcerEffect());
    }

    @Override
    public String getRule() {
        return "{this} can't be blocked except by black creatures.";
    }

    @Override
    public ZulaportEnforcerAbility copy() {
        return getInstance();
    }
}

class ZulaportEnforcerEffect extends RestrictionEffect<ZulaportEnforcerEffect> {

    public ZulaportEnforcerEffect() {
        super(Duration.WhileOnBattlefield);
    }

    public ZulaportEnforcerEffect(final ZulaportEnforcerEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getAbilities().containsKey(ZulaportEnforcerAbility.getInstance().getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (blocker.getColor().isBlack()) {
            return true;
        }
        return false;
    }

    @Override
    public ZulaportEnforcerEffect copy() {
        return new ZulaportEnforcerEffect(this);
    }
}
