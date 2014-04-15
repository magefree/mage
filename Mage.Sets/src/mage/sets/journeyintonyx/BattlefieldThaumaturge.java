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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class BattlefieldThaumaturge extends CardImpl<BattlefieldThaumaturge> {

    public BattlefieldThaumaturge(UUID ownerId) {
        super(ownerId, 32, "Battlefield Thaumaturge", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Each instant and sorcery spell you cast costs 1 less to cast for each creature it targets.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BattlefieldThaumaturgeSpellsCostReductionEffect()));
        // Heroic - Whenever you cast a spell that targets Battlefield Thaumaturge, Battlefield Thaumaturge gains hexproof until end of turn.
        this.addAbility(new HeroicAbility(new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.EndOfTurn)));
    }

    public BattlefieldThaumaturge(final BattlefieldThaumaturge card) {
        super(card);
    }

    @Override
    public BattlefieldThaumaturge copy() {
        return new BattlefieldThaumaturge(this);
    }
}

class BattlefieldThaumaturgeSpellsCostReductionEffect extends CostModificationEffectImpl<BattlefieldThaumaturgeSpellsCostReductionEffect> {

    private static final FilterSpell filter = new FilterSpell("instant and sorcery spell");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
    }

    public BattlefieldThaumaturgeSpellsCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "Each instant and sorcery spell you cast costs 1 less to cast for each creature it targets";
    }

    protected BattlefieldThaumaturgeSpellsCostReductionEffect(BattlefieldThaumaturgeSpellsCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int creatureTargets = 0;
        for (Target target: abilityToModify.getTargets()) {
            for (UUID uuid: target.getTargets()) {
                Permanent permanent = game.getPermanent(uuid); 
                if (permanent != null && permanent.getCardType().contains(CardType.CREATURE)) {
                    creatureTargets++;
                }
            }
        }
        CardUtil.reduceCost(abilityToModify, creatureTargets);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility)
                && abilityToModify.getControllerId().equals(source.getControllerId())) {
            Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
            return spell != null && this.filter.match(spell, game);
        }
        return false;
    }

    @Override
    public BattlefieldThaumaturgeSpellsCostReductionEffect copy() {
        return new BattlefieldThaumaturgeSpellsCostReductionEffect(this);
    }
}
