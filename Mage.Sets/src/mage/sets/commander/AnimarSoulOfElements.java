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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class AnimarSoulOfElements extends CardImpl<AnimarSoulOfElements> {

    private static final FilterSpell filterSpell = new FilterSpell("a creature spell");
    private static final FilterCard filter = new FilterCard("white and from black");
    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLACK)));
        filterSpell.add(new CardTypePredicate(CardType.CREATURE));
    }

    public AnimarSoulOfElements(UUID ownerId) {
        super(ownerId, 181, "Animar, Soul of Elements", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{U}{R}{G}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Elemental");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from white and from black
        this.addAbility(new ProtectionAbility(filter));

        // Whenever you cast a creature spell, put a +1/+1 counter on Animar, Soul of Elements.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filterSpell, false));

        // Creature spells you cast cost {1} less to cast for each +1/+1 counter on Animar.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AnimarCostReductionEffect()));

    }

    public AnimarSoulOfElements(final AnimarSoulOfElements card) {
        super(card);
    }

    @Override
    public AnimarSoulOfElements copy() {
        return new AnimarSoulOfElements(this);
    }
}

class AnimarCostReductionEffect extends CostModificationEffectImpl<AnimarCostReductionEffect> {

    AnimarCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Creature spells you cast cost {1} less to cast for each +1/+1 counter on Animar";
    }

    AnimarCostReductionEffect(AnimarCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Ability spellAbility = (SpellAbility) abilityToModify;
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null && spellAbility != null) {
            int amount = sourcePermanent.getCounters().getCount(CounterType.P1P1);
            if (amount > 0) {
                CardUtil.reduceCost(spellAbility, amount);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility || abilityToModify instanceof FlashbackAbility || abilityToModify instanceof RetraceAbility) {
            Card sourceCard = game.getCard(abilityToModify.getSourceId());
            if (sourceCard != null && abilityToModify.getControllerId().equals(source.getControllerId()) && (sourceCard.getCardType().contains(CardType.CREATURE))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AnimarCostReductionEffect copy() {
        return new AnimarCostReductionEffect(this);
    }

}
