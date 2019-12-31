
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SourceCostReductionForEachCardInGraveyardEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BedlamReveler extends CardImpl {

    private static final DynamicValue cardsCount = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);

    public BedlamReveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
        this.subtype.add(SubType.DEVIL, SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Bedlam Reveler costs {1} less to cast for each instant or sorcery card in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SourceCostReductionForEachCardInGraveyardEffect(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY)
        ).addHint(new ValueHint("Instant and sorcery cards in your graveyard", cardsCount)));

        // Prowess
        this.addAbility(new ProwessAbility());

        // When Bedlam Reveler enters the battlefield, discard your hand, then draw three cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DiscardHandControllerEffect().setText("discard your hand,")
        );
        ability.addEffect(new DrawCardSourceControllerEffect(3).setText("then draw three cards"));
        this.addAbility(ability);
    }

    private BedlamReveler(final BedlamReveler card) {
        super(card);
    }

    @Override
    public BedlamReveler copy() {
        return new BedlamReveler(this);
    }
}
