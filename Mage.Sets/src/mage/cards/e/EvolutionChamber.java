package mage.cards.e;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author NinthWorld
 */
public final class EvolutionChamber extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zerg creature tokens");

    static {
        filter.add(new TokenPredicate());
        filter.add(new SubtypePredicate(SubType.ZERG));
    }

    public EvolutionChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        

        // Zerg creature tokens get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));
    }

    public EvolutionChamber(final EvolutionChamber card) {
        super(card);
    }

    @Override
    public EvolutionChamber copy() {
        return new EvolutionChamber(this);
    }
}
