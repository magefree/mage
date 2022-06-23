package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
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
public final class SkolaGrovedancer extends CardImpl {

    public SkolaGrovedancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a land card is put into your graveyard from anywhere, you gain 1 life.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new GainLifeEffect(1), false, StaticFilters.FILTER_CARD_LAND_A, TargetController.YOU
        ));

        // {2}{G}: Put the top card of your library into your graveyard.
        this.addAbility(new SimpleActivatedAbility(
                new MillCardsControllerEffect(1), new ManaCostsImpl<>("{2}{G}")
        ));
    }

    private SkolaGrovedancer(final SkolaGrovedancer card) {
        super(card);
    }

    @Override
    public SkolaGrovedancer copy() {
        return new SkolaGrovedancer(this);
    }
}
