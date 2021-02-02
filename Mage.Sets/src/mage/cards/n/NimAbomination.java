
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LoneFox
 *
 */
public final class NimAbomination extends CardImpl {

    public NimAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, if Nim Abomination is untapped, you lose 3 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new LoseLifeSourceControllerEffect(3),
                TargetController.YOU, SourceUntappedCondition.instance, false));
    }

    private NimAbomination(final NimAbomination card) {
        super(card);
    }

    @Override
    public NimAbomination copy() {
        return new NimAbomination(this);
    }
}

enum SourceUntappedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null) {
            return !permanent.isTapped();
        }
        return false;
    }

    @Override
    public String toString() {
        return "if {this} is untapped";
    }
}
