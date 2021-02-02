
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author Loki
 */
public final class LuminousAngel extends CardImpl {

    public LuminousAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken(), 1), TargetController.YOU, true));
    }

    private LuminousAngel(final LuminousAngel card) {
        super(card);
    }

    @Override
    public LuminousAngel copy() {
        return new LuminousAngel(this);
    }
}
