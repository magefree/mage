package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommonCrook extends CardImpl {

    public CommonCrook(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature dies, create a Treasure token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new TreasureToken())));
    }

    private CommonCrook(final CommonCrook card) {
        super(card);
    }

    @Override
    public CommonCrook copy() {
        return new CommonCrook(this);
    }
}
