
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BladesOfVelisVel extends CardImpl {

    public BladesOfVelisVel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{1}{R}");
        this.subtype.add(SubType.SHAPESHIFTER);

        // Changeling
        this.addAbility(ChangelingAbility.getInstance());
        
        // Up to two target creatures each get +2/+0 and gain all creature types until end of turn.
        Effect effect = new BoostTargetEffect(2,0, Duration.EndOfTurn);
        effect.setText("Up to two target creatures each get +2/+0");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(ChangelingAbility.getInstance(), Duration.EndOfTurn, "and gain all creature types until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0,2));        
    }

    public BladesOfVelisVel(final BladesOfVelisVel card) {
        super(card);
    }

    @Override
    public BladesOfVelisVel copy() {
        return new BladesOfVelisVel(this);
    }
}
