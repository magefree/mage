package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
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
public final class SelflessSamurai extends CardImpl {

    public SelflessSamurai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a Samurai or Warrior you control attacks alone, it gains lifelink until end of turn.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(
                new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                        .setText("it gains lifelink until end of turn"),
                StaticFilters.FILTER_CONTROLLED_SAMURAI_OR_WARRIOR, true, false
        ));

        // Sacrifice Selfless Samurai: Another target creature you control gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private SelflessSamurai(final SelflessSamurai card) {
        super(card);
    }

    @Override
    public SelflessSamurai copy() {
        return new SelflessSamurai(this);
    }
}
