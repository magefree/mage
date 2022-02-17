package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OathOfTheAncientWood extends CardImpl {

    public OathOfTheAncientWood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever Oath of the Ancient Wood or another enchantment enters the battlefield under your control, you may put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_PERMANENT_ENCHANTMENT, true, true
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private OathOfTheAncientWood(final OathOfTheAncientWood card) {
        super(card);
    }

    @Override
    public OathOfTheAncientWood copy() {
        return new OathOfTheAncientWood(this);
    }
}
