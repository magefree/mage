package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.TargetObjectMatchesFilterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HazardrootHerbalist extends CardImpl {

    private static final Condition condition = new TargetObjectMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_TOKEN);

    public HazardrootHerbalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever you attack, target creature you control gets +1/+0 until end of turn. If that creature is a token, it also gains deathtouch until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new BoostTargetEffect(1, 0), 1);
        ability.addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new GainAbilityTargetEffect(DeathtouchAbility.getInstance())),
                condition, "if that creature is a token, it also gains deathtouch until end of turn"
        ));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private HazardrootHerbalist(final HazardrootHerbalist card) {
        super(card);
    }

    @Override
    public HazardrootHerbalist copy() {
        return new HazardrootHerbalist(this);
    }
}
