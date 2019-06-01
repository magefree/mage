package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SpiderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwinSilkSpider extends CardImpl {

    public TwinSilkSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Twin-Silk Spider enters the battlefield, create a 1/2 green Spider creature token with reach.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SpiderToken())));
    }

    private TwinSilkSpider(final TwinSilkSpider card) {
        super(card);
    }

    @Override
    public TwinSilkSpider copy() {
        return new TwinSilkSpider(this);
    }
}
