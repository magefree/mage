package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class SmotheringAbomination extends CardImpl {

    public SmotheringAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, sacrifice a creature
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeControllerEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, 1, null), TargetController.YOU, false));

        // Whenever you sacrifice a creature, draw a card.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_PERMANENT_CREATURE
        ));
    }

    private SmotheringAbomination(final SmotheringAbomination card) {
        super(card);
    }

    @Override
    public SmotheringAbomination copy() {
        return new SmotheringAbomination(this);
    }
}
