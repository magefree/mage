
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesChosenCreatureTypeSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class ProteusMachine extends CardImpl {

    public ProteusMachine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Morph {0}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{0}")));
        
        // When Proteus Machine is turned face up, it becomes the creature type of your choice. (This effect lasts indefinitely.)
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new BecomesChosenCreatureTypeSourceEffect(false, Duration.Custom)));
    }

    private ProteusMachine(final ProteusMachine card) {
        super(card);
    }

    @Override
    public ProteusMachine copy() {
        return new ProteusMachine(this);
    }
}
