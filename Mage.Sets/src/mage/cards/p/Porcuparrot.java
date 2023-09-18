package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SourceMutatedCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Porcuparrot extends CardImpl {

    public Porcuparrot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Mutate {2}{R}
        this.addAbility(new MutateAbility(this, "{2}{R}"));

        // {T}: This creature deals X damage to any target, where X is the number of times this creature has mutated.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(SourceMutatedCount.instance)
                        .setText("this creature deals X damage to any target, " +
                                "where X is the number of times this creature has mutated"),
                new TapSourceCost()
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Porcuparrot(final Porcuparrot card) {
        super(card);
    }

    @Override
    public Porcuparrot copy() {
        return new Porcuparrot(this);
    }
}
