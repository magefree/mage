package mage.cards.p;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 * @author TheElk801
 */
public final class PlagueNurse extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("each other creature you control with toxic");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new AbilityPredicate(ToxicAbility.class));
    }

    public PlagueNurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Toxic 2
        this.addAbility(new ToxicAbility(2));

        // {2}{G}: Each other creature you control with toxic gains toxic 1 until end of turn. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilityAllEffect(new ToxicAbility(1), Duration.EndOfTurn, filter),
                new ManaCostsImpl<>("{2}{G}")
        ));
    }

    private PlagueNurse(final PlagueNurse card) {
        super(card);
    }

    @Override
    public PlagueNurse copy() {
        return new PlagueNurse(this);
    }
}
