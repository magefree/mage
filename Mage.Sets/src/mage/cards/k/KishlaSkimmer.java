package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KishlaSkimmer extends CardImpl {

    public KishlaSkimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a card leaves your graveyard during your turn, draw a card. This ability triggers only once each turn.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_CARD, true
        ).setTriggerPhrase("Whenever a card leaves your graveyard during your turn, ").setTriggersLimitEachTurn(1));
    }

    private KishlaSkimmer(final KishlaSkimmer card) {
        super(card);
    }

    @Override
    public KishlaSkimmer copy() {
        return new KishlaSkimmer(this);
    }
}
