package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShantottoTacticianMagician extends CardImpl {

    public ShantottoTacticianMagician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Whenever you cast a noncreature spell, Shantotto, Tactician Magician gets +X/+0 until end of turn, where X is the amount of mana spent to cast that spell. If X is 4 or greater, draw a card.
        Ability ability = new SpellCastControllerTriggeredAbility(new BoostSourceEffect(
                ShantottoTacticianMagicianValue.instance,
                StaticValue.get(0), Duration.EndOfTurn
        ), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false);
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                ShantottoTacticianMagicianCondition.instance,
                "If X is 4 or more, draw a card"
        ));
        this.addAbility(ability);
    }

    private ShantottoTacticianMagician(final ShantottoTacticianMagician card) {
        super(card);
    }

    @Override
    public ShantottoTacticianMagician copy() {
        return new ShantottoTacticianMagician(this);
    }
}

enum ShantottoTacticianMagicianValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Spell) effect.getValue("spellCast"))
                .map(spell -> ManaPaidSourceWatcher.getTotalPaid(spell.getId(), game))
                .orElse(0);
    }

    @Override
    public ShantottoTacticianMagicianValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the amount of mana spent to cast that spell";
    }

    @Override
    public String toString() {
        return "X";
    }
}

enum ShantottoTacticianMagicianCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .getEffectValueFromAbility(source, "spellCast", Spell.class)
                .map(spell -> ManaPaidSourceWatcher.getTotalPaid(spell.getId(), game) >= 4)
                .orElse(false);
    }
}
