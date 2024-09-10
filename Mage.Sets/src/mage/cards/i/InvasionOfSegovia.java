package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Kraken11Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfSegovia extends CardImpl {

    public InvasionOfSegovia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{2}{U}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(4);
        this.secondSideCardClazz = mage.cards.c.CaetusSeaTyrantOfSegovia.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Segovia enters the battlefield, create two 1/1 blue Kraken creature tokens with trample.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Kraken11Token(), 2)));
    }

    private InvasionOfSegovia(final InvasionOfSegovia card) {
        super(card);
    }

    @Override
    public InvasionOfSegovia copy() {
        return new InvasionOfSegovia(this);
    }
}
