
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInAllHandsCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class MultaniMaroSorcerer extends CardImpl {

    public MultaniMaroSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());
        
        // Multani, Maro-Sorcerer's power and toughness are each equal to the total number of cards in all players' hands.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(CardsInAllHandsCount.instance)));
    }

    private MultaniMaroSorcerer(final MultaniMaroSorcerer card) {
        super(card);
    }

    @Override
    public MultaniMaroSorcerer copy() {
        return new MultaniMaroSorcerer(this);
    }
}
