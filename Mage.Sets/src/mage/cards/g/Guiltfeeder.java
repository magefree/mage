
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInTargetPlayersGraveyardCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class Guiltfeeder extends CardImpl {

    public Guiltfeeder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Fear
        this.addAbility(FearAbility.getInstance());
        // Whenever Guiltfeeder attacks and isn't blocked, defending player loses 1 life for each card in their graveyard.
        Effect effect = new LoseLifeTargetEffect(new CardsInTargetPlayersGraveyardCount());
        effect.setText("defending player loses 1 life for each card in their graveyard");
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(effect, false, true));
    }

    private Guiltfeeder(final Guiltfeeder card) {
        super(card);
    }

    @Override
    public Guiltfeeder copy() {
        return new Guiltfeeder(this);
    }
}
