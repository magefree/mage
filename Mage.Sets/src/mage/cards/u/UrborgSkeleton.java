
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class UrborgSkeleton extends CardImpl {

    private static final String staticText = "If {this} was kicked, it enters the battlefield with a +1/+1 counter on it.";

    public UrborgSkeleton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Kicker {3} (You may pay an additional {3} as you cast this spell.)
        this.addAbility(new KickerAbility("{3}"));

        // {B}: Regenerate Urborg Skeleton.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")));

        // If Urborg Skeleton was kicked, it enters the battlefield with a +1/+1 counter on it.
        Ability ability = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
                KickedCondition.ONCE, staticText, "");
        this.addAbility(ability);
    }

    private UrborgSkeleton(final UrborgSkeleton card) {
        super(card);
    }

    @Override
    public UrborgSkeleton copy() {
        return new UrborgSkeleton(this);
    }
}
