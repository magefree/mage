package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.SeekCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Svyatoslav28
 */

public final class HollowhengeWrangler extends CardImpl {

    public HollowhengeWrangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Hollowhenge Wrangler enters the battlefield, seek a land card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SeekCardEffect(StaticFilters.FILTER_CARD_LAND)));

        // Discard a land card: Conjure a card named Hollowhenge Beast into your hand.
        // You may also activate this ability while Hollowhenge Wrangler is in your graveyard.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.ALL, new ConjureCardEffect("Hollowhenge Beast"), new DiscardCardCost(StaticFilters.FILTER_CARD_LAND), HollowhengeWranglerCondition.instance
        );
        this.addAbility(ability);

    }


    private HollowhengeWrangler(final HollowhengeWrangler card) {
        super(card);
    }

    @Override
    public HollowhengeWrangler copy() {
        return new HollowhengeWrangler(this);
    }
}

enum HollowhengeWranglerCondition implements Condition {
    instance;
    private static final List<Zone> zones = Arrays.asList(Zone.BATTLEFIELD, Zone.GRAVEYARD);

    @Override
    public boolean apply(Game game, Ability source) {
        return zones.contains(game.getState().getZone(source.getSourceId()));
    }

    @Override
    public String toString() {
        return "You may also activate this ability while {this} is in your graveyard";
    }
}
