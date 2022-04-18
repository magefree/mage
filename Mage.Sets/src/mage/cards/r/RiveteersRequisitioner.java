package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiveteersRequisitioner extends CardImpl {

    public RiveteersRequisitioner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Riveteers Requisitioner dies, create a Treasure token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Blitz {2}{R}
        this.addAbility(new BlitzAbility("{2}{R}"));
    }

    private RiveteersRequisitioner(final RiveteersRequisitioner card) {
        super(card);
    }

    @Override
    public RiveteersRequisitioner copy() {
        return new RiveteersRequisitioner(this);
    }
}
