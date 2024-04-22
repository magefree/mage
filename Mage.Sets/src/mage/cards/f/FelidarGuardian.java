package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class FelidarGuardian extends CardImpl {

    public FelidarGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Felidar Guardian enters the battlefield, you may exile another target permanent you control, then return that card to the battlefield under its owner's control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileThenReturnTargetEffect(false, true), true);
        ability.addTarget(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_TARGET_PERMANENT));
        this.addAbility(ability);
    }

    private FelidarGuardian(final FelidarGuardian card) {
        super(card);
    }

    @Override
    public FelidarGuardian copy() {
        return new FelidarGuardian(this);
    }
}
