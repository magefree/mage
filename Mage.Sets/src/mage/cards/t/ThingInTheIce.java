
package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

/**
 * @author fireshoes
 */
public final class ThingInTheIce extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public ThingInTheIce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.a.AwokenHorror.class;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Thing in the Ice enters the battlefield with four ice counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.ICE.createInstance(4));
        effect.setText("with four ice counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));

        // Whenever you cast an instant or sorcery spell, remove an ice counter from Thing in the Ice. Then if it has no ice counters on it, transform it.
        this.addAbility(new TransformAbility());
        effect = new RemoveCounterSourceEffect(CounterType.ICE.createInstance(1));
        effect.setText("remove an ice counter from {this}");
        Ability ability = new SpellCastControllerTriggeredAbility(effect, filter, false);
        effect = new ConditionalOneShotEffect(new TransformSourceEffect(), new SourceHasCounterCondition(CounterType.ICE, 0, 0),
                "if there are no ice counters on it, transform it");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private ThingInTheIce(final ThingInTheIce card) {
        super(card);
    }

    @Override
    public ThingInTheIce copy() {
        return new ThingInTheIce(this);
    }
}
