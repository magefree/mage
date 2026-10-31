package mage.cards.u;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author muz
 */
public final class USSEnterpriseESovereign extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creatures");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public USSEnterpriseESovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // When U.S.S. Enterprise-E enters, destroy all tapped creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(filter)));

        // Station
        this.addAbility(new StationAbility());

        // STATION 8+
        // Flying
        // Vigilance
        // 7/7
        this.addAbility(new StationLevelAbility(8)
            .withLevelAbility(FlyingAbility.getInstance())
            .withLevelAbility(VigilanceAbility.getInstance())
            .withPT(7, 7));
    }

    private USSEnterpriseESovereign(final USSEnterpriseESovereign card) {
        super(card);
    }

    @Override
    public USSEnterpriseESovereign copy() {
        return new USSEnterpriseESovereign(this);
    }
}
