package mage.cards.b;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class BlizzardBrawl extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("you control three or more snow permanents");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 2);

    public BlizzardBrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        this.supertype.add(SuperType.SNOW);

        // Choose target creature you control and target creature you don't control.
        // If you control three or more snow permanents, the creature you control gets +1/+0 and gains indestructible until end of turn.
        this.getSpellAbility().addEffect(
                new ConditionalOneShotEffect(
                        new AddContinuousEffectToGame(new BoostTargetEffect(1, 0)),
                        condition,
                        "Choose target creature you control and target creature you don't control. " +
                        "If you control three or more snow permanents, the creature you control gets +1/+0 " +
                        "and gains indestructible until end of turn."
                ).addEffect(
                        new AddContinuousEffectToGame(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()))
                )
        );

        // Then those creatures fight each other.
        this.getSpellAbility().addEffect(new FightTargetsEffect()
                .setText("Then those creatures fight each other. <i>(Each deals damage equal to its power to the other.)</i>")
        );
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private BlizzardBrawl(final BlizzardBrawl card) {
        super(card);
    }

    @Override
    public BlizzardBrawl copy() {
        return new BlizzardBrawl(this);
    }
}
