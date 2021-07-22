package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WebweaverChangeling extends CardImpl {

    private static final Condition condition
            = new CardsInControllerGraveyardCondition(3, StaticFilters.FILTER_CARD_CREATURE);

    public WebweaverChangeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Webweaver Changeling enters the battlefield, if there are three or more creature cards in your graveyard, you gain 5 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainLifeEffect(5)), condition,
                "When {this} enters the battlefield, if there are three or more " +
                        "creature cards in your graveyard, you gain 5 life."
        ));
    }

    private WebweaverChangeling(final WebweaverChangeling card) {
        super(card);
    }

    @Override
    public WebweaverChangeling copy() {
        return new WebweaverChangeling(this);
    }
}
