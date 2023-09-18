
package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author MajorLazar
 */
public final class ApocalypseDemon extends CardImpl {

    public ApocalypseDemon(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        subtype.add(SubType.DEMON);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Apocalypse Demon's power and toughness are each equal to the number of cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new CardsInControllerGraveyardCount())));

        // At the beginning of your upkeep, tap Apocalypse Demon unless you sacrifice another creature.
        TapSourceUnlessPaysEffect tapEffect = new TapSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        tapEffect.setText("tap {this} unless you sacrifice another creature.");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(tapEffect, TargetController.YOU, false));
    }

    private ApocalypseDemon(final ApocalypseDemon card) {
        super(card);
    }

    @Override
    public ApocalypseDemon copy() {
        return new ApocalypseDemon(this);
    }
}
