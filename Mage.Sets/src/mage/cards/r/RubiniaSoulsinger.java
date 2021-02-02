
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RubiniaSoulsinger extends CardImpl {

    public RubiniaSoulsinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{W}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // You may choose not to untap Rubinia Soulsinger during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // {tap}: Gain control of target creature for as long as you control Rubinia and Rubinia remains tapped.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.OneUse),
                new RubiniaSoulsingerCondition(),
                "Gain control of target creature for as long as you control Rubinia and Rubinia remains tapped");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RubiniaSoulsinger(final RubiniaSoulsinger card) {
        super(card);
    }

    @Override
    public RubiniaSoulsinger copy() {
        return new RubiniaSoulsinger(this);
    }
}

class RubiniaSoulsingerCondition implements Condition {

    private UUID controllerId;

    @Override
    public boolean apply(Game game, Ability source) {
        if (controllerId == null) {
            controllerId = source.getControllerId();
        }
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null) {
            if (permanent.isTapped()) {
                return controllerId.equals(source.getControllerId());
            }
        }
        return false;
    }
}
