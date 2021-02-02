
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class BurrentonShieldBearers extends CardImpl {

    public BurrentonShieldBearers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.KITHKIN, SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(0, 3, Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BurrentonShieldBearers(final BurrentonShieldBearers card) {
        super(card);
    }

    @Override
    public BurrentonShieldBearers copy() {
        return new BurrentonShieldBearers(this);
    }
}
