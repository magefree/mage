package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class MagmabloodArchaic extends CardImpl {

    public MagmabloodArchaic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2/R}{2/R}{2/R}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Converge -- This creature enters with a +1/+1 counter on it for each color of mana spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(
            new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), ColorsOfManaSpentToCastCount.getInstance()
            ), null, AbilityWord.CONVERGE.formatWord() + "{this} enters with a +1/+1 counter " +
            "on it for each color of mana spent to cast it.", null
        ));

        // Whenever you cast an instant or sorcery spell, creatures you control get +1/+0 until end of turn for each color of mana spent to cast that spell.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new BoostControlledEffect(MagmabloodArchaicManaSpentValue.instance, StaticValue.get(0), Duration.EndOfTurn),
            StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private MagmabloodArchaic(final MagmabloodArchaic card) {
        super(card);
    }

    @Override
    public MagmabloodArchaic copy() {
        return new MagmabloodArchaic(this);
    }
}

enum MagmabloodArchaicManaSpentValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil
                .getEffectValueFromAbility(sourceAbility, "spellCast", Spell.class)
                .map(Spell::getStackAbility)
                .map(Ability::getManaCostsToPay)
                .map(manaCosts -> manaCosts.getUsedManaToPay().getDifferentColors())
                .orElse(0);
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "color of mana spent to cast that spell";
    }

    @Override
    public String toString() {
        return "1";
    }
}
