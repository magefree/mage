package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PestBlackGreenAttacksToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PestbroodSloth extends CardImpl {

    public PestbroodSloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.SLOTH);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When this creature dies, create two 1/1 black and green Pest creature tokens with "Whenever this token attacks, you gain 1 life."
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new PestBlackGreenAttacksToken(), 2)));
    }

    private PestbroodSloth(final PestbroodSloth card) {
        super(card);
    }

    @Override
    public PestbroodSloth copy() {
        return new PestbroodSloth(this);
    }
}
