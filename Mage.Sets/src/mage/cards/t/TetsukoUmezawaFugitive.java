

package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;

/**
 * @author JRHerlehy
 *         Created on 4/7/18.
 */
public final class TetsukoUmezawaFugitive extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(Predicates.or(
                new PowerPredicate(ComparisonType.FEWER_THAN, 2),
                new ToughnessPredicate(ComparisonType.FEWER_THAN, 2)
        ));
    }

    public TetsukoUmezawaFugitive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Creatures you control with power or toughness 1 or less can't be blocked.
        Effect effect = new CantBeBlockedAllEffect(filter, Duration.Custom);
        effect.setText("Creatures you control with power or toughness 1 or less can't be blocked");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private TetsukoUmezawaFugitive(final TetsukoUmezawaFugitive card) {
        super(card);
    }

    @Override
    public TetsukoUmezawaFugitive copy() {
        return new TetsukoUmezawaFugitive(this);
    }
}
