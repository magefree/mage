package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ThopterColorlessToken;

/**
 *
 * @author TheElk801
 */
public final class AviationPioneer extends CardImpl {

    public AviationPioneer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Aviation Pioneer enters the battlefield, create a 1/1 colorless Thopter creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new ThopterColorlessToken())
        ));
    }

    private AviationPioneer(final AviationPioneer card) {
        super(card);
    }

    @Override
    public AviationPioneer copy() {
        return new AviationPioneer(this);
    }
}
