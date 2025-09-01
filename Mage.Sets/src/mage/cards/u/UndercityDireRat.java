package mage.cards.u;

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
public final class UndercityDireRat extends CardImpl {

    public UndercityDireRat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Rat Tail -- When this creature dies, create a Treasure token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new TreasureToken())).withFlavorWord("Rat Tail"));
    }

    private UndercityDireRat(final UndercityDireRat card) {
        super(card);
    }

    @Override
    public UndercityDireRat copy() {
        return new UndercityDireRat(this);
    }
}
