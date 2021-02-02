
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public final class KeeperOfTresserhorn extends CardImpl {

    public KeeperOfTresserhorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Keeper of Tresserhorn attacks and isn't blocked, it assigns no combat damage this turn and defending player loses 2 life.
        Effect effect = new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn);
        effect.setText("it assigns no combat damage this turn");
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(effect, false, true);
        effect = new LoseLifeTargetEffect(2);
        effect.setText("and defending player loses 2 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private KeeperOfTresserhorn(final KeeperOfTresserhorn card) {
        super(card);
    }

    @Override
    public KeeperOfTresserhorn copy() {
        return new KeeperOfTresserhorn(this);
    }
}
