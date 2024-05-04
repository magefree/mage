
package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainControlAllControlledTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HellkiteTyrant extends CardImpl {

    public HellkiteTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Hellkite Tyrant deals combat damage to a player, gain control of all artifacts that player controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new GainControlAllControlledTargetEffect(StaticFilters.FILTER_PERMANENT_ARTIFACTS)
                        .setText("gain control of all artifacts that player controls"),
                false, true));

        // At the beginning of your upkeep, if you control twenty or more artifacts, you win the game.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect(), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability,
                new PermanentsOnTheBattlefieldCondition(new FilterArtifactPermanent(), ComparisonType.MORE_THAN, 19),
                "At the beginning of your upkeep, if you control twenty or more artifacts, you win the game."
        ).addHint(new ValueHint("Artifacts you control", new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT))));
    }

    private HellkiteTyrant(final HellkiteTyrant card) {
        super(card);
    }

    @Override
    public HellkiteTyrant copy() {
        return new HellkiteTyrant(this);
    }
}