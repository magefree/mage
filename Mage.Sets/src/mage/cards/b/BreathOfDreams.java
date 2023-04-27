
package mage.cards.b;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author L_J
 */
public final class BreathOfDreams extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Green creatures");

    static{
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public BreathOfDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}{U}");

        // Cumulative upkeep-Pay {U}.
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{U}")));

        // Green creatures have "Cumulative upkeep {1}."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")), Duration.WhileOnBattlefield, filter)));
    }

    private BreathOfDreams(final BreathOfDreams card) {
        super(card);
    }

    @Override
    public BreathOfDreams copy() {
        return new BreathOfDreams(this);
    }
}
