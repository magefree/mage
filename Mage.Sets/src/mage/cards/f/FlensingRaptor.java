package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlensingRaptor extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another creature you control with toxic");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new AbilityPredicate(ToxicAbility.class));
    }

    public FlensingRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // When Flensing Raptor enters the battlefield, another target creature you control with toxic gets +1/+1 and gains flying until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(1, 1)
                .setText("another target creature you control with toxic gets +1/+1"));
        ability.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText("and gains flying until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private FlensingRaptor(final FlensingRaptor card) {
        super(card);
    }

    @Override
    public FlensingRaptor copy() {
        return new FlensingRaptor(this);
    }
}
