package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.AirbendTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AppaLoyalSkyBison extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("another target nonland permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public AppaLoyalSkyBison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BISON);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Appa enters or attacks, choose one --
        // * Target creature you control gains flying until end of turn.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());

        // * Airbend another target nonland permanent you control.
        ability.addMode(new Mode(new AirbendTargetEffect()).addTarget(new TargetPermanent(filter)));
        this.addAbility(ability);
    }

    private AppaLoyalSkyBison(final AppaLoyalSkyBison card) {
        super(card);
    }

    @Override
    public AppaLoyalSkyBison copy() {
        return new AppaLoyalSkyBison(this);
    }
}
