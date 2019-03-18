package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.cost.SourceCostReductionForEachCardInGraveyardEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CrypticSerpent extends CardImpl {

    private static final DynamicValue cardsCount = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);

    public CrypticSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Cryptic Serpent costs {1} less to cast for each instant and sorcery card in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SourceCostReductionForEachCardInGraveyardEffect(new FilterInstantOrSorceryCard()))
                .addHint(new ValueHint("Instant and sorcery card in your graveyard", cardsCount)));
    }

    public CrypticSerpent(final CrypticSerpent card) {
        super(card);
    }

    @Override
    public CrypticSerpent copy() {
        return new CrypticSerpent(this);
    }
}
