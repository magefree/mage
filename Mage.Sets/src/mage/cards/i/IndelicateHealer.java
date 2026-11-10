package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author muz
 */
public final class IndelicateHealer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Klingon or Warrior");

    static {
        filter.add(Predicates.or(
            SubType.KLINGON.getPredicate(),
            SubType.WARRIOR.getPredicate()
        ));
    }

    public IndelicateHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever this creature or another Klingon or Warrior you control enters, creatures you control gain lifelink until end of turn.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
            new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(),
                Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
            ),
            filter, false, true
        ));
    }

    private IndelicateHealer(final IndelicateHealer card) {
        super(card);
    }

    @Override
    public IndelicateHealer copy() {
        return new IndelicateHealer(this);
    }
}
