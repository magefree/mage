package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.continuous.AddCreatureTypeAdditionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PortalToPhyrexia extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card from a graveyard");

    public PortalToPhyrexia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{9}");

        // When Portal to Phyrexia enters the battlefield, each opponent sacrifices three creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeOpponentsEffect(3, StaticFilters.FILTER_PERMANENT_CREATURES)
        ));

        // At the beginning of your upkeep, put target creature card from a graveyard onto the battlefield under your control. It's a Phyrexian in addition to its other types.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), TargetController.YOU, false
        );
        ability.addEffect(new AddCreatureTypeAdditionEffect(SubType.PHYREXIAN, false));
        ability.addTarget(new TargetCardInGraveyard(filter));
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
