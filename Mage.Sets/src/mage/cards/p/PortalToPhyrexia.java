package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOntoBattlefieldTargetEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PortalToPhyrexia extends CardImpl {

    public PortalToPhyrexia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{9}");

        // When Portal to Phyrexia enters the battlefield, each opponent sacrifices three creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeOpponentsEffect(3, StaticFilters.FILTER_PERMANENT_CREATURES)
        ));

        // At the beginning of your upkeep, put target creature card from a graveyard onto the battlefield under your control. It's a Phyrexian in addition to its other types.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new PutOntoBattlefieldTargetEffect(false).withContinuousEffects(
                        "It's a Phyrexian in addition to its other types",
                        new AddCardSubTypeTargetEffect(SubType.PHYREXIAN, Duration.Custom)
                ), TargetController.YOU, false
        );
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));
        this.addAbility(ability);
    }

    private PortalToPhyrexia(final PortalToPhyrexia card) {
        super(card);
    }

    @Override
    public PortalToPhyrexia copy() {
        return new PortalToPhyrexia(this);
    }
}
