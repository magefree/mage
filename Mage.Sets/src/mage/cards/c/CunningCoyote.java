package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CunningCoyote extends CardImpl {

    public CunningCoyote(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.COYOTE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Cunning Coyote enters the battlefield, another target creature you control gets +1/+1 and gains haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(1, 1)
                .setText("another target creature you control gets +1/+1"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText("and gains haste until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);

        // Plot {1}{R}
        this.addAbility(new PlotAbility("{1}{R}"));
    }

    private CunningCoyote(final CunningCoyote card) {
        super(card);
    }

    @Override
    public CunningCoyote copy() {
        return new CunningCoyote(this);
    }
}
