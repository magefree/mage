package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public final class AnimarSoulOfElements extends CardImpl {

    public AnimarSoulOfElements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from white and from black
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE, ObjectColor.BLACK));

        // Whenever you cast a creature spell, put a +1/+1 counter on Animar, Soul of Elements.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), StaticFilters.FILTER_SPELL_A_CREATURE, false));

        // Creature spells you cast cost {1} less to cast for each +1/+1 counter on Animar.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AnimarCostReductionEffect()));

    }

    private AnimarSoulOfElements(final AnimarSoulOfElements card) {
        super(card);
    }

    @Override
    public AnimarSoulOfElements copy() {
        return new AnimarSoulOfElements(this);
    }
}

class AnimarCostReductionEffect extends CostModificationEffectImpl {

    AnimarCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Creature spells you cast cost {1} less to cast for each +1/+1 counter on Animar";
    }

    AnimarCostReductionEffect(AnimarCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Ability spellAbility = abilityToModify;
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null && spellAbility != null) {
            int amount = sourcePermanent.getCounters(game).getCount(CounterType.P1P1);
            CardUtil.reduceCost(spellAbility, amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (abilityToModify.isControlledBy(source.getControllerId())) {
                Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
                if (spellCard != null) {
                    return spellCard.isCreature(game);
                }
            }
        }
        return false;
    }

    @Override
    public AnimarCostReductionEffect copy() {
        return new AnimarCostReductionEffect(this);
    }

}
