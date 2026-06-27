package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TheWaspJanetVanDyne extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("tapped creature an opponent controls");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public TheWaspJanetVanDyne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When The Wasp enters, she deals 4 damage to target tapped creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(4));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TheWaspJanetVanDyne(final TheWaspJanetVanDyne card) {
        super(card);
    }

    @Override
    public TheWaspJanetVanDyne copy() {
        return new TheWaspJanetVanDyne(this);
    }
}
