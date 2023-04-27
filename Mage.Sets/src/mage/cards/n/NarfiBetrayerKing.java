package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NarfiBetrayerKing extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("snow and Zombie creatures");

    static {
        filter.add(Predicates.or(
                SuperType.SNOW.getPredicate(),
                SubType.ZOMBIE.getPredicate()
        ));
    }

    public NarfiBetrayerKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Other snow and Zombie creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // {S}{S}{S}: Return Narfi, Betrayer King from your graveyard to the battlefield tapped.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(true, false),
                new ManaCostsImpl<>("{S}{S}{S}")
        ));
    }

    private NarfiBetrayerKing(final NarfiBetrayerKing card) {
        super(card);
    }

    @Override
    public NarfiBetrayerKing copy() {
        return new NarfiBetrayerKing(this);
    }
}
