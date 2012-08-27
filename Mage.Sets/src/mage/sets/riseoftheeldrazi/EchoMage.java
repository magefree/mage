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

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public class EchoMage extends CardImpl<EchoMage> {

    private final static FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public EchoMage(UUID ownerId) {
        super(ownerId, 64, "Echo Mage", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Level up {1}{U}
        this.addAbility(new LevelUpAbility(new ManaCostsImpl("{1}{U}")));
        // LEVEL 2-3
        // 2/4
        // {U}{U}, {tap}: Copy target instant or sorcery spell. You may choose new targets for the copy.
        Abilities<Ability> abilities1 = new AbilitiesImpl<Ability>();
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CopyTargetSpellEffect(), new ManaCostsImpl("{U}{U}"));
        ability.addTarget(new TargetSpell(filter));
        abilities1.add(ability);
        // LEVEL 4+
        // 2/5
        // {U}{U}, {tap}: Copy target instant or sorcery spell twice. You may choose new targets for the copies.
        Abilities<Ability> abilities2 = new AbilitiesImpl<Ability>();
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EchoMageEffect(), new ManaCostsImpl("{U}{U}"));
        ability.addTarget(new TargetSpell(filter));
        abilities2.add(ability);

        LevelerCardBuilder.construct(this,
                new LevelerCardBuilder.LevelAbility(2, 3, abilities1, 2, 4),
                new LevelerCardBuilder.LevelAbility(4, -1, abilities2, 2, 5));
    }

    public EchoMage(final EchoMage card) {
        super(card);
    }

    @Override
    public EchoMage copy() {
        return new EchoMage(this);
    }
}

class EchoMageEffect extends OneShotEffect<EchoMageEffect> {

    public EchoMageEffect() {
        super(Outcome.Copy);
        this.staticText = "Copy target instant or sorcery spell twice. You may choose new targets for the copies";
    }

    public EchoMageEffect(final EchoMageEffect effect) {
        super(effect);
    }

    @Override
    public EchoMageEffect copy() {
        return new EchoMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            for (int i = 0; i < 2; i++) {
                Spell copy = spell.copySpell();
                copy.setControllerId(source.getControllerId());
                copy.setCopiedSpell(true);
                game.getStack().push(copy);
                copy.chooseNewTargets(game, source.getControllerId());
            }
            return true;
        }
        return false;
    }
}
