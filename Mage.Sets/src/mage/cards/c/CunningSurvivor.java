
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleOrDiscardControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author emerald000
 */
public final class CunningSurvivor extends CardImpl {

    public CunningSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cycle or discard a card, Cunning Survivor gets +1/+0 until end of turn and can't be blocked this turn.
        Ability ability = new CycleOrDiscardControllerTriggeredAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn));
        Effect effect = new CantBeBlockedSourceEffect(Duration.EndOfTurn);
        effect.setText("and can't be blocked this turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private CunningSurvivor(final CunningSurvivor card) {
        super(card);
    }

    @Override
    public CunningSurvivor copy() {
        return new CunningSurvivor(this);
    }
}
