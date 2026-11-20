package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class KtingaClassBattleCruiser extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Klingons and/or Warriors you control");

    static {
        filter.add(Predicates.or(
            SubType.KLINGON.getPredicate(),
            SubType.WARRIOR.getPredicate()
        ));
    }

    public KtingaClassBattleCruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.SPACECRAFT);

        // Whenever this Spacecraft enters or attacks, one or two target Klingons and/or Warriors you control gain flying until end of turn.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new GainAbilityTargetEffect(FlyingAbility.getInstance())
        );
        ability.addTarget(new TargetPermanent(1, 2, filter));
        this.addAbility(ability);

        // Station
        this.addAbility(new StationAbility());

        // STATION 4+
        // Flying
        // 5/5
        this.addAbility(new StationLevelAbility(4)
            .withLevelAbility(FlyingAbility.getInstance())
            .withPT(5, 5));
    }

    private KtingaClassBattleCruiser(final KtingaClassBattleCruiser card) {
        super(card);
    }

    @Override
    public KtingaClassBattleCruiser copy() {
        return new KtingaClassBattleCruiser(this);
    }
}
