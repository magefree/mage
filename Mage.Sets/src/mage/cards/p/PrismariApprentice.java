package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrismariApprentice extends CardImpl {

    public PrismariApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, Prismari Apprentice can't be blocked this turn. If that spell has mana value 5 or greater, put a +1/+1 counter on Prismari Apprentice.
        Ability ability = new MagecraftAbility(new CantBeBlockedSourceEffect(Duration.EndOfTurn));
        ability.addEffect(new ConditionalOneShotEffect(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance()), PrismariApprenticeCondition.instance,
                "If that spell has mana value 5 or greater, put a +1/+1 counter on {this}"
        ));
        this.addAbility(ability);
    }

    private PrismariApprentice(final PrismariApprentice card) {
        super(card);
    }

    @Override
    public PrismariApprentice copy() {
        return new PrismariApprentice(this);
    }
}

enum PrismariApprenticeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) source.getEffects().get(0).getValue(MagecraftAbility.SPELL_KEY);
        return spell != null && spell.getManaValue() >= 5;
    }
}
