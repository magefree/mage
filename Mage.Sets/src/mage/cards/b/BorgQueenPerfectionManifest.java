package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AssimilateTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class BorgQueenPerfectionManifest extends CardImpl {

    public BorgQueenPerfectionManifest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BORG);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Artifact creatures you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
            2, 0, Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));

        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AssimilateTargetEffect());
        ability.addTarget(new TargetCardInOpponentsGraveyard(new FilterCreatureCard("target creature card from an opponent's graveyard")));
        this.addAbility(ability);
    }

    private BorgQueenPerfectionManifest(final BorgQueenPerfectionManifest card) {
        super(card);
    }

    @Override
    public BorgQueenPerfectionManifest copy() {
        return new BorgQueenPerfectionManifest(this);
    }
}
