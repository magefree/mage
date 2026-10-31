package mage.cards.i;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author muz
 */
public final class IKSNeghVarImperialFlagship extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Klingons and Warriors");

    static {
        filter.add(Predicates.or(
            SubType.KLINGON.getPredicate(),
            SubType.WARRIOR.getPredicate()
        ));
    }

    public IKSNeghVarImperialFlagship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // Klingons and Warriors you control have vigilance.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Station
        this.addAbility(new StationAbility());

        // STATION 10+
        // Flying
        // Vigilance
        // 6/6
        this.addAbility(new StationLevelAbility(10)
            .withLevelAbility(FlyingAbility.getInstance())
            .withLevelAbility(VigilanceAbility.getInstance())
            .withPT(6, 6));
    }

    private IKSNeghVarImperialFlagship(final IKSNeghVarImperialFlagship card) {
        super(card);
    }

    @Override
    public IKSNeghVarImperialFlagship copy() {
        return new IKSNeghVarImperialFlagship(this);
    }
}
