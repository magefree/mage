package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NecrogenRotpriest extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with toxic");

    static {
        filter.add(new AbilityPredicate(ToxicAbility.class));
    }

    public NecrogenRotpriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Toxic 2
        this.addAbility(new ToxicAbility(2));

        // Whenever a creature you control with toxic deals combat damage to a player, that player gets an additional poison counter.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new AddPoisonCounterTargetEffect(1)
                        .setText("that player gets an additional poison counter"),
                filter, false, SetTargetPointer.PLAYER, true, true
        ));

        // {1}{B}{G}: Target creature you control with toxic gains deathtouch until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
                        .setText("target creature you control with toxic gains deathtouch until end of turn"),
                new ManaCostsImpl<>("{1}{B}{G}")
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private NecrogenRotpriest(final NecrogenRotpriest card) {
        super(card);
    }

    @Override
    public NecrogenRotpriest copy() {
        return new NecrogenRotpriest(this);
    }
}
