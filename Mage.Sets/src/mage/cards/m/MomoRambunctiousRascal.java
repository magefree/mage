package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MomoRambunctiousRascal extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent("tapped creature an opponent controls");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public MomoRambunctiousRascal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LEMUR);
        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Momo enters, he deals 4 damage to target tapped creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(4, "he"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MomoRambunctiousRascal(final MomoRambunctiousRascal card) {
        super(card);
    }

    @Override
    public MomoRambunctiousRascal copy() {
        return new MomoRambunctiousRascal(this);
    }
}
