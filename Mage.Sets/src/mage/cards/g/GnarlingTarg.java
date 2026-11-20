package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author muz
 */
public final class GnarlingTarg extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Klingons and Warriors");

    static {
        filter.add(Predicates.or(
            SubType.KLINGON.getPredicate(),
            SubType.WARRIOR.getPredicate()
        ));
    }

    public GnarlingTarg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you attack, Klingons and Warriors you control get +2/+0 and gain trample until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new BoostControlledEffect(
            2, 0, Duration.EndOfTurn, filter
        ).setText("Klingons and Warriors you control get +2/+0"), 1);
        ability.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filter)
            .setText("and gain trample until end of turn"));
        this.addAbility(ability);
    }

    private GnarlingTarg(final GnarlingTarg card) {
        super(card);
    }

    @Override
    public GnarlingTarg copy() {
        return new GnarlingTarg(this);
    }
}
