package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClarionSpirit extends CardImpl {

    public ClarionSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast your second spell each turn, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new CastSecondSpellTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken())));
    }

    private ClarionSpirit(final ClarionSpirit card) {
        super(card);
    }

    @Override
    public ClarionSpirit copy() {
        return new ClarionSpirit(this);
    }
}
