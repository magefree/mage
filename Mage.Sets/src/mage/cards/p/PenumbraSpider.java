package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PenumbraSpiderToken;

/**
 *
 * @author jonubuu
 */
public final class PenumbraSpider extends CardImpl {

    public PenumbraSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Penumbra Spider dies, create a 2/4 black Spider creature token with reach.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new PenumbraSpiderToken()), false));
    }

    private PenumbraSpider(final PenumbraSpider card) {
        super(card);
    }

    @Override
    public PenumbraSpider copy() {
        return new PenumbraSpider(this);
    }
}
