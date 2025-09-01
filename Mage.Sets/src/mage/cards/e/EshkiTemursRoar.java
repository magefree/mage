package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EshkiTemursRoar extends CardImpl {

    public EshkiTemursRoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a creature spell, put a +1/+1 counter on Eshki. If that spell's power is 4 or greater, draw a card. If that spell's power is 6 or greater, Eshki deals damage equal to Eshki's power to each opponent.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_SPELL_A_CREATURE, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), EshkiTemursRoarCondition.FOUR,
                "If that spell's power is 4 or greater, draw a card"
        ));
        ability.addEffect(new ConditionalOneShotEffect(
                new DamagePlayersEffect(SourcePermanentPowerValue.NOT_NEGATIVE, TargetController.OPPONENT),
                EshkiTemursRoarCondition.SIX, "If that spell's power is 6 or greater, " +
                "{this} deals damage equal to {this}'s power to each opponent"
        ));
        this.addAbility(ability);
    }

    private EshkiTemursRoar(final EshkiTemursRoar card) {
        super(card);
    }

    @Override
    public EshkiTemursRoar copy() {
        return new EshkiTemursRoar(this);
    }
}

enum EshkiTemursRoarCondition implements Condition {
    FOUR(4),
    SIX(6);
    private final int amount;

    EshkiTemursRoarCondition(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .getEffectValueFromAbility(source, "spellCast", Spell.class)
                .map(Spell::getPower)
                .map(MageInt::getValue)
                .filter(x -> x >= amount)
                .isPresent();
    }
}
