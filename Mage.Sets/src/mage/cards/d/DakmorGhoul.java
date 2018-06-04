
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class DakmorGhoul extends CardImpl {

    public DakmorGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Dakmor Ghoul enters the battlefield, target opponent loses 2 life and you gain 2 life.
        Effect effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(2), false);
        ability.addTarget(new TargetOpponent());
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public DakmorGhoul(final DakmorGhoul card) {
        super(card);
    }

    @Override
    public DakmorGhoul copy() {
        return new DakmorGhoul(this);
    }
}
