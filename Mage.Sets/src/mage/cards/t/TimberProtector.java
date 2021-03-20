
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author Loki
 */
public final class TimberProtector extends CardImpl {

    private static final FilterCreaturePermanent filterTreefolk = new FilterCreaturePermanent("Treefolk creatures");
    private static final FilterControlledPermanent filterBoth = new FilterControlledPermanent("Other Treefolk and Forests you control");

    static {
        filterTreefolk.add(SubType.TREEFOLK.getPredicate());
        filterBoth.add(Predicates.or(
                SubType.TREEFOLK.getPredicate(),
                SubType.FOREST.getPredicate()));
        filterBoth.add(AnotherPredicate.instance);
    }

    public TimberProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Other Treefolk creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterTreefolk, true)));
        // Other Treefolk and Forests you control are indestructible.
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filterBoth, true);
        effect.setText("Other Treefolk and Forests you control are indestructible");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private TimberProtector(final TimberProtector card) {
        super(card);
    }

    @Override
    public TimberProtector copy() {
        return new TimberProtector(this);
    }
}
