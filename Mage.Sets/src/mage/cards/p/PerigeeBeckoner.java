package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PerigeeBeckoner extends CardImpl {

    public PerigeeBeckoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When this creature enters, until end of turn, another target creature you control gets +2/+0 and gains "When this creature dies, return it to the battlefield tapped under its owner's control."
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 0)
                .setText("until end of turn, another target creature you control gets +2/+0"));
        ability.addEffect(new GainAbilityTargetEffect(new DiesSourceTriggeredAbility(
                new ReturnSourceFromGraveyardToBattlefieldEffect(true, true), false
        ), Duration.EndOfTurn, "and gains \"When this creature dies, " +
                "return it to the battlefield tapped under its owner's control.\""));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);

        // Warp {1}{B}
        this.addAbility(new WarpAbility(this, "{1}{B}"));
    }

    private PerigeeBeckoner(final PerigeeBeckoner card) {
        super(card);
    }

    @Override
    public PerigeeBeckoner copy() {
        return new PerigeeBeckoner(this);
    }
}
