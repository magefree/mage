package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SpiritWorldToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FoggySwampSpiritKeeper extends CardImpl {

    public FoggySwampSpiritKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you draw your second card each turn, create a 1/1 colorless Spirit creature token with "This token can't block or be blocked by non-Spirit creatures."
        this.addAbility(new DrawNthCardTriggeredAbility(new CreateTokenEffect(new SpiritWorldToken())));
    }

    private FoggySwampSpiritKeeper(final FoggySwampSpiritKeeper card) {
        super(card);
    }

    @Override
    public FoggySwampSpiritKeeper copy() {
        return new FoggySwampSpiritKeeper(this);
    }
}
