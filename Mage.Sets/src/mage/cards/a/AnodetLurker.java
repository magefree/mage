
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class AnodetLurker extends CardImpl {

    public AnodetLurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Anodet Lurker dies, you gain 3 life.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(3)));
    }

    private AnodetLurker(final AnodetLurker card) {
        super(card);
    }

    @Override
    public AnodetLurker copy() {
        return new AnodetLurker(this);
    }
}
