package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HermitDruid extends CardImpl {

    public HermitDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {G}, {tap}: Reveal cards from the top of your library until you reveal a basic land card. Put that card into your hand and all other cards revealed this way into your graveyard.
        Ability ability = new SimpleActivatedAbility(new RevealCardsFromLibraryUntilEffect(
                StaticFilters.FILTER_CARD_BASIC_LAND, PutCards.HAND, PutCards.GRAVEYARD
        ), new ManaCostsImpl<>("{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private HermitDruid(final HermitDruid card) {
        super(card);
    }

    @Override
    public HermitDruid copy() {
        return new HermitDruid(this);
    }
}
