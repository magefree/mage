package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SacrificeSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
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
public final class DaemogothWoeEater extends CardImpl {

    public DaemogothWoeEater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B/G}{G}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, sacrifice a creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeControllerEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, 1, ""
        ), TargetController.YOU, false));

        // When you sacrifice Daemogoth Woe-Eater, each opponent discards a card, you draw a card, and you gain 2 life.
        Ability ability = new SacrificeSourceTriggeredAbility(
                new DiscardEachPlayerEffect(TargetController.OPPONENT), false
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", you"));
        ability.addEffect(new GainLifeEffect(2).concatBy(", and"));
        this.addAbility(ability);
    }

    private DaemogothWoeEater(final DaemogothWoeEater card) {
        super(card);
    }

    @Override
    public DaemogothWoeEater copy() {
        return new DaemogothWoeEater(this);
    }
}
