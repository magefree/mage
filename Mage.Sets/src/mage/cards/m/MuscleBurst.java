
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class MuscleBurst extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                new NamePredicate("Muscle Burst"),
                new AbilityPredicate(CountAsMuscleBurstAbility.class)
        ));
    }

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            new CardsInAllGraveyardsCount(filter), StaticValue.get(3)
    );

    public MuscleBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +X/+X until end of turn, where X is 3 plus the number of cards named Muscle Burst in all graveyards.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                xValue, xValue, Duration.EndOfTurn, true
        ).setText("Target creature gets +X/+X until end of turn, where X is 3 plus the number of cards named Muscle Burst in all graveyards."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MuscleBurst(final MuscleBurst card) {
        super(card);
    }

    @Override
    public MuscleBurst copy() {
        return new MuscleBurst(this);
    }

    public static Ability getCountAsAbility() {
        return new CountAsMuscleBurstAbility();
    }
}

class CountAsMuscleBurstAbility extends SimpleStaticAbility {

    public CountAsMuscleBurstAbility() {
        super(Zone.GRAVEYARD, new InfoEffect("If {this} is in a graveyard, effects from spells named Muscle Burst count it as a card named Muscle Burst"));
    }

    private CountAsMuscleBurstAbility(CountAsMuscleBurstAbility ability) {
        super(ability);
    }

    @Override
    public CountAsMuscleBurstAbility copy() {
        return new CountAsMuscleBurstAbility(this);
    }
}
