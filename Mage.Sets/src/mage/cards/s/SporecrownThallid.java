
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author TheElk801
 */
public final class SporecrownThallid extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each other creature you control that's a Fungus or Saproling");

    static {
        filter.add(Predicates.or(
                SubType.FUNGUS.getPredicate(),
                SubType.SAPROLING.getPredicate()
        ));
    }

    public SporecrownThallid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each other creature you control that's a Fungus or Saproling gets +1/+1.
        this.addAbility(
                new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)
                        .setText("Each other creature you control that's a Fungus or Saproling gets +1/+1"))
        );
    }

    private SporecrownThallid(final SporecrownThallid card) {
        super(card);
    }

    @Override
    public SporecrownThallid copy() {
        return new SporecrownThallid(this);
    }
}
