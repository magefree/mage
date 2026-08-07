package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
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
public final class ShuttleCrew extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent("tapped creature an opponent controls");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public ShuttleCrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, it deals 4 damage to target tapped creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(4));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ShuttleCrew(final ShuttleCrew card) {
        super(card);
    }

    @Override
    public ShuttleCrew copy() {
        return new ShuttleCrew(this);
    }
}
