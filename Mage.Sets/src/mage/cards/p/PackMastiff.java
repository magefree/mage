package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PackMastiff extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new NamePredicate("Pack Mastiff"));
    }

    public PackMastiff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{R}: Each creature you control named Pack Mastiff gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, filter
        ).setText("Each creature you control named Pack Mastiff gets +1/+0 until end of turn."), new ManaCostsImpl<>("{1}{R}")));
    }

    private PackMastiff(final PackMastiff card) {
        super(card);
    }

    @Override
    public PackMastiff copy() {
        return new PackMastiff(this);
    }
}
