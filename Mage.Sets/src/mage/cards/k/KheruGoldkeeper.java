package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.RenewAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KheruGoldkeeper extends CardImpl {

    public KheruGoldkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more cards leave your graveyard during your turn, create a Treasure token.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()),
                StaticFilters.FILTER_CARD_CARDS, true
        ));

        // Renew -- {2}{B}{G}{U}, Exile this card from your graveyard: Put two +1/+1 counters and a flying counter on target creature. Activate only as a sorcery.
        this.addAbility(new RenewAbility(
                "{2}{B}{G}{U}",
                CounterType.P1P1.createInstance(2),
                CounterType.FLYING.createInstance()
        ));
    }

    private KheruGoldkeeper(final KheruGoldkeeper card) {
        super(card);
    }

    @Override
    public KheruGoldkeeper copy() {
        return new KheruGoldkeeper(this);
    }
}
