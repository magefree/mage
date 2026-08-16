package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.BearToken;
import mage.game.stack.Spell;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class DancingFromDarkToDawn extends CardImpl {

    public DancingFromDarkToDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        // Whenever you cast a creature spell, put X +1/+1 counters on target creature you control, where X is that spell's mana value.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(), DancingFromDarkToDawnValue.instance)
                .setText("put X +1/+1 counters on target creature you control, where X is that spell's mana value"),
            StaticFilters.FILTER_SPELL_A_CREATURE, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Landfall -- Whenever a land you control enters, create a 2/2 green Bear creature token.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new BearToken())));
    }

    private DancingFromDarkToDawn(final DancingFromDarkToDawn card) {
        super(card);
    }

    @Override
    public DancingFromDarkToDawn copy() {
        return new DancingFromDarkToDawn(this);
    }
}

enum DancingFromDarkToDawnValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Spell spell = (Spell) effect.getValue("spellCast");
        return spell != null ? spell.getManaValue() : 0;
    }

    @Override
    public DancingFromDarkToDawnValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "that spell's mana value";
    }
}
