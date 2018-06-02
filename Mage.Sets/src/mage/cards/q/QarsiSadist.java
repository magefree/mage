
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class QarsiSadist extends CardImpl {

    public QarsiSadist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Exploit
        this.addAbility(new ExploitAbility());
        // When Qarsi Sadist exploits a creature, target opponent loses 2 life and you gain 2 life.
        Ability ability = new ExploitCreatureTriggeredAbility(new LoseLifeTargetEffect(2), false);
        ability.addTarget(new TargetOpponent());
        Effect effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public QarsiSadist(final QarsiSadist card) {
        super(card);
    }

    @Override
    public QarsiSadist copy() {
        return new QarsiSadist(this);
    }
}
