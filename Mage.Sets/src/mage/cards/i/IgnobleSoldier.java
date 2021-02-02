
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class IgnobleSoldier extends CardImpl {

    public IgnobleSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Ignoble Soldier becomes blocked, prevent all combat damage that would be dealt by it this turn.
        Effect effect = new PreventCombatDamageBySourceEffect(Duration.EndOfTurn);
        effect.setText("prevent all combat damage that would be dealt by it this turn");
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(effect, false));
    }

    private IgnobleSoldier(final IgnobleSoldier card) {
        super(card);
    }

    @Override
    public IgnobleSoldier copy() {
        return new IgnobleSoldier(this);
    }
}
