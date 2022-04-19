package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DuskMangler extends CardImpl {

    public DuskMangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // As an additional cost to cast this spell, sacrifice a creature, discard a card, or pay 4 life.
        this.getSpellAbility().addCost(new OrCost(
                "sacrifice a creature, discard a card, or pay 4 life",
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT),
                new DiscardCardCost(), new PayLifeCost(4)
        ));

        // When Dusk Mangler enters the battlefield, each opponent sacrifices a creature, discards a card, and loses 4 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_A_CREATURE)
        );
        ability.addEffect(new DiscardEachPlayerEffect(TargetController.OPPONENT).setText(", discards a card"));
        ability.addEffect(new LoseLifeOpponentsEffect(4).setText(", and loses 4 life"));
        this.addAbility(ability);
    }

    private DuskMangler(final DuskMangler card) {
        super(card);
    }

    @Override
    public DuskMangler copy() {
        return new DuskMangler(this);
    }
}
