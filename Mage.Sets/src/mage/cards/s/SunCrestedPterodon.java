
package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 * @author JayDi85
 */
public final class SunCrestedPterodon extends CardImpl {

    private static final FilterControlledCreaturePermanent filterAnotherDino = new FilterControlledCreaturePermanent();
    static {
        filterAnotherDino.add(AnotherPredicate.instance);
        filterAnotherDino.add(SubType.DINOSAUR.getPredicate());
    }

    public SunCrestedPterodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sun-Crested Pterodon has vigilance as long as you control another Dinosaur.
        ContinuousEffect effect = new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield);
        Condition condition = new PermanentsOnTheBattlefieldCondition(filterAnotherDino);
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(effect, condition,
                "{this} has vigilance as long as you control another Dinosaur.")
        ));
    }

    private SunCrestedPterodon(final SunCrestedPterodon card) {
        super(card);
    }

    @Override
    public SunCrestedPterodon copy() {
        return new SunCrestedPterodon(this);
    }
}