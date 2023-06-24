
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ForestwalkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author North
 */
public final class MirriCatWarrior extends CardImpl {

    public MirriCatWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new ForestwalkAbility());
        this.addAbility(VigilanceAbility.getInstance());
    }

    private MirriCatWarrior(final MirriCatWarrior card) {
        super(card);
    }

    @Override
    public MirriCatWarrior copy() {
        return new MirriCatWarrior(this);
    }
}
