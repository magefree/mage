package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightLuminary extends CardImpl {

    public KnightLuminary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters, create a 1/1 white Human Soldier creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanSoldierToken())));

        // Warp {1}{W}
        this.addAbility(new WarpAbility(this, "{1}{W}"));
    }

    private KnightLuminary(final KnightLuminary card) {
        super(card);
    }

    @Override
    public KnightLuminary copy() {
        return new KnightLuminary(this);
    }
}
