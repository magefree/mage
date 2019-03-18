
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Styxo
 */
public final class FelidarGuardian extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another target permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public FelidarGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Felidar Guardian enters the battlefield, you may exile another target permanent you control, then return that card to the battlefield under its owner's control.
        Effect effect = new ExileTargetForSourceEffect();
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, true);
        ability.addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect());
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);

    }

    public FelidarGuardian(final FelidarGuardian card) {
        super(card);
    }

    @Override
    public FelidarGuardian copy() {
        return new FelidarGuardian(this);
    }
}
