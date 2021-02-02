
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth

 */
public final class KinscaerHarpoonist extends CardImpl {

    public KinscaerHarpoonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Kinscaer Harpoonist attacks, you may have target creature lose flying until end of turn.
        Effect effect = new LoseAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("you may have target creature lose flying until end of turn");
        Ability ability = new AttacksTriggeredAbility(effect, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
    }

    private KinscaerHarpoonist(final KinscaerHarpoonist card) {
        super(card);
    }

    @Override
    public KinscaerHarpoonist copy() {
        return new KinscaerHarpoonist(this);
    }
}
