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
package mage.sets.commander2015;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.counter.AddCountersControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class MizzixOfTheIzmagnus extends CardImpl {

    private static final FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell("an instant or sorcery spell with converted mana cost greater than the number of experience counters you have");

    static {
        filter.add(new MizzixOfTheIzmagnusPredicate());
    }

    public MizzixOfTheIzmagnus(UUID ownerId) {
        super(ownerId, 50, "Mizzix of the Izmagnus", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");
        this.expansionSetCode = "C15";
        this.supertype.add("Legendary");
        this.subtype.add("Goblin");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an instant or sorcery spell with converted mana cost greater than the number of experience counters you have, you get an experience counter.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersControllerEffect(CounterType.EXPERIENCE.createInstance(1), false), filter, false));

        // Instant and sorcery spells you cast cost {1} less to cast for each experience counter you have.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MizzixOfTheIzmagnusCostReductionEffect()));
    }

    public MizzixOfTheIzmagnus(final MizzixOfTheIzmagnus card) {
        super(card);
    }

    @Override
    public MizzixOfTheIzmagnus copy() {
        return new MizzixOfTheIzmagnus(this);
    }
}

class MizzixOfTheIzmagnusPredicate implements Predicate<MageObject> {

    @Override
    public boolean apply(MageObject input, Game game) {
        Spell spell = game.getStack().getSpell(input.getId());
        if (spell != null) {
            Player controller = game.getPlayer(spell.getControllerId());
            if (controller != null) {
                if (spell.getConvertedManaCost() > controller.getCounters().getCount(CounterType.EXPERIENCE)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "VariableManaCost";
    }
}

class MizzixOfTheIzmagnusCostReductionEffect extends CostModificationEffectImpl {

    MizzixOfTheIzmagnusCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Instant and sorcery spells you cast cost {1} less to cast for each experience counter you have";
    }

    MizzixOfTheIzmagnusCostReductionEffect(MizzixOfTheIzmagnusCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            SpellAbility spellAbility = (SpellAbility) abilityToModify;
            CardUtil.adjustCost(spellAbility, controller.getCounters().getCount(CounterType.EXPERIENCE));
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility && abilityToModify.getControllerId().equals(source.getControllerId())) {
            Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
            if (spell != null) {
                return new FilterInstantOrSorceryCard().match(spell, source.getSourceId(), source.getControllerId(), game);
            } else {
                // used at least for flashback ability because Flashback ability doesn't use stack or for getPlayables where spell is not cast yet
                Card sourceCard = game.getCard(abilityToModify.getSourceId());
                return sourceCard != null && new FilterInstantOrSorceryCard().match(sourceCard, source.getSourceId(), source.getControllerId(), game);
            }
        }
        return false;
    }

    @Override
    public MizzixOfTheIzmagnusCostReductionEffect copy() {
        return new MizzixOfTheIzmagnusCostReductionEffect(this);
    }
}
