package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpringLoadedSawblades extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("tapped creature an opponent controls");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public SpringLoadedSawblades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");
        this.secondSideCardClazz = mage.cards.b.BladewheelChariot.class;

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Spring-Loaded Sawblades enters the battlefield, it deals 5 damage to target tapped creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(5, "it"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Craft with artifact {3}{W}
        this.addAbility(new CraftAbility("{3}{W}"));
    }

    private SpringLoadedSawblades(final SpringLoadedSawblades card) {
        super(card);
    }

    @Override
    public SpringLoadedSawblades copy() {
        return new SpringLoadedSawblades(this);
    }
}
