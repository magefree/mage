
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class InquisitorExarch extends CardImpl {

    public InquisitorExarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2));
        Mode mode = new Mode(new LoseLifeTargetEffect(2));
        mode.addTarget(new TargetPlayer());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private InquisitorExarch(final InquisitorExarch card) {
        super(card);
    }

    @Override
    public InquisitorExarch copy() {
        return new InquisitorExarch(this);
    }
}
