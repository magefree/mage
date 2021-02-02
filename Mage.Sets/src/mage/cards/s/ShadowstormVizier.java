
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleOrDiscardControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author anonymous
 */
public final class ShadowstormVizier extends CardImpl {

    public ShadowstormVizier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cycle or discard a card, Shadowstorm Vizier gets +1/+1 until end of turn.
        this.addAbility(new CycleOrDiscardControllerTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false));
    }

    private ShadowstormVizier(final ShadowstormVizier card) {
        super(card);
    }

    @Override
    public ShadowstormVizier copy() {
        return new ShadowstormVizier(this);
    }
}
