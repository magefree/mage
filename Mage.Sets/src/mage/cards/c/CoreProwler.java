

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class CoreProwler extends CardImpl {

    public CoreProwler (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new DiesTriggeredAbility(new ProliferateEffect()));
    }

    public CoreProwler (final CoreProwler card) {
        super(card);
    }

    @Override
    public CoreProwler copy() {
        return new CoreProwler(this);
    }

}
