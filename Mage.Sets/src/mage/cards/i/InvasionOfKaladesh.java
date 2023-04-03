package mage.cards.i;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ThopterColorlessToken;

/**
 *
 * @author TheElk801
 */
public final class InvasionOfKaladesh extends CardImpl {

    public InvasionOfKaladesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{U}{R}");
        
        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(4);this.secondSideCardClazz=mage.cards.a.AetherwingGoldenScaleFlagship.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SimpleStaticAbility(new InfoEffect(
                "<i>(As a Siege enters, choose an opponent to protect it. " +
                        "You and others can attack it. When it's defeated, exile it, then cast it transformed.)</i>"
        )));

        // When Invasion of Kaladesh enters the battlefield, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ThopterColorlessToken())));
    }

    private InvasionOfKaladesh(final InvasionOfKaladesh card) {
        super(card);
    }

    @Override
    public InvasionOfKaladesh copy() {
        return new InvasionOfKaladesh(this);
    }
}
