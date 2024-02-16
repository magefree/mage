
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;

/**
 *
 * @author Darkside-
 */
public final class EnigmaDrake extends CardImpl {

    public EnigmaDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{R}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Enigma Drakes's power is equal to the number of instant and sorcery cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(
                new CardsInControllerGraveyardCount(new FilterInstantOrSorceryCard("instant and sorcery cards")))));
    }

    private EnigmaDrake(final EnigmaDrake card) {
        super(card);
    }

    @Override
    public EnigmaDrake copy() {
        return new EnigmaDrake(this);
    }
}
