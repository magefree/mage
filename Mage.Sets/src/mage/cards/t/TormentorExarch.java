
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class TormentorExarch extends CardImpl {

    public TormentorExarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 0, Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        Mode mode = new Mode(new BoostTargetEffect(0, -2, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private TormentorExarch(final TormentorExarch card) {
        super(card);
    }

    @Override
    public TormentorExarch copy() {
        return new TormentorExarch(this);
    }
}
