package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KraulForagers extends CardImpl {

    public KraulForagers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Undergrowth — When Kraul Foragers enters the battlefield, you gain 1 life for each creature card in your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(
                new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)
        ).setText("you gain 1 life for each creature card in your graveyard"), false).setAbilityWord(AbilityWord.UNDERGROWTH));
    }

    private KraulForagers(final KraulForagers card) {
        super(card);
    }

    @Override
    public KraulForagers copy() {
        return new KraulForagers(this);
    }
}
