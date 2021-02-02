
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleOrDiscardControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author stravant
 */
public final class PitilessVizier extends CardImpl {

    public PitilessVizier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever you cycle or discard a card, Pitiless Vizier gains indestructible until end of turn.
        addAbility(new CycleOrDiscardControllerTriggeredAbility(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn)));
    }

    private PitilessVizier(final PitilessVizier card) {
        super(card);
    }

    @Override
    public PitilessVizier copy() {
        return new PitilessVizier(this);
    }
}
