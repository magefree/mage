
package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainControlAllControlledTargetEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HellkiteTyrant extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterArtifactPermanent("you control twenty or more artifacts"), ComparisonType.MORE_THAN, 19
    );

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
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect())
                .withInterveningIf(condition).addHint(ArtifactYouControlHint.instance));
    }

    private HellkiteTyrant(final HellkiteTyrant card) {
        super(card);
    }

    @Override
    public HellkiteTyrant copy() {
        return new HellkiteTyrant(this);
    }
}
