
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author muz
 */
public final class LysAlanaInformant extends CardImpl {

    public LysAlanaInformant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When this creature enters or dies, surveil 1.
        Ability ability = new EntersBattlefieldOrDiesSourceTriggeredAbility(new SurveilEffect(1), false);
        this.addAbility(ability);
    }

    private LysAlanaInformant(final LysAlanaInformant card) {
        super(card);
    }

    @Override
    public LysAlanaInformant copy() {
        return new LysAlanaInformant(this);
    }
}
