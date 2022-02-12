package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GenerousVisitor extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an enchantment spell");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public GenerousVisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast an enchantment spell, put a +1/+1 counter on target creature.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), filter, false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GenerousVisitor(final GenerousVisitor card) {
        super(card);
    }

    @Override
    public GenerousVisitor copy() {
        return new GenerousVisitor(this);
    }
}
