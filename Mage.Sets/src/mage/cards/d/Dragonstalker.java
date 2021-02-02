
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author LevelX2
 */
public final class Dragonstalker extends CardImpl {

    public Dragonstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // protection from Dragons
        this.addAbility(new ProtectionAbility(new FilterPermanent(SubType.DRAGON, "Dragons")));
    }

    private Dragonstalker(final Dragonstalker card) {
        super(card);
    }

    @Override
    public Dragonstalker copy() {
        return new Dragonstalker(this);
    }
}
