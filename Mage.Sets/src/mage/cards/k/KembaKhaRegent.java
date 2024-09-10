
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.CatToken;

/**
 *
 * @author Loki, North
 */
public final class KembaKhaRegent extends CardImpl {

    public KembaKhaRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new CatToken(), new EquipmentAttachedCount()),
                TargetController.YOU, false));
    }

    private KembaKhaRegent(final KembaKhaRegent card) {
        super(card);
    }

    @Override
    public KembaKhaRegent copy() {
        return new KembaKhaRegent(this);
    }
}
