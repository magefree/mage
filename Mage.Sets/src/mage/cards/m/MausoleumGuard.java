package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class MausoleumGuard extends CardImpl {

    public MausoleumGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Mausoleum Guard dies, create two 1/1 white Spirit creature tokens with flying.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken(), 2)));
    }

    private MausoleumGuard(final MausoleumGuard card) {
        super(card);
    }

    @Override
    public MausoleumGuard copy() {
        return new MausoleumGuard(this);
    }
}
