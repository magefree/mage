
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PhantasmalAbomination extends CardImpl {

    public PhantasmalAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(new SourceBecomesTargetTriggeredAbility(new SacrificeSourceEffect().setText("sacrifice it")));
    }

    private PhantasmalAbomination(final PhantasmalAbomination card) {
        super(card);
    }

    @Override
    public PhantasmalAbomination copy() {
        return new PhantasmalAbomination(this);
    }
}
