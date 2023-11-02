package mage.cards.g;

import mage.MageInt;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GuardianOfTheGreatDoor extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("untapped artifacts, creatures, and/or lands you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public GuardianOfTheGreatDoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As an additional cost to cast this spell, tap four untapped artifacts, creatures, and/or lands you control.
        this.getSpellAbility().addCost(new TapTargetCost(new TargetControlledPermanent(4, filter)));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

    }

    private GuardianOfTheGreatDoor(final GuardianOfTheGreatDoor card) {
        super(card);
    }

    @Override
    public GuardianOfTheGreatDoor copy() {
        return new GuardianOfTheGreatDoor(this);
    }
}
