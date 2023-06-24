
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.AttackedThisTurnSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 * @author xenohedron
 */

public final class MadDog extends CardImpl {

    public MadDog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, if Mad Dog didn't attack or come under your control this turn, sacrifice it.
        Condition condition = new InvertCondition(new OrCondition(AttackedThisTurnSourceCondition.instance, MadDogCondition.instance));
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new BeginningOfEndStepTriggeredAbility(
                new SacrificeSourceEffect(), TargetController.YOU, false), condition,
                "At the beginning of your end step, if {this} didn't attack or come under your control this turn, sacrifice it");
        ability.addWatcher(new AttackedThisTurnWatcher());
        this.addAbility(ability);

    }

    private MadDog(final MadDog card) {
        super(card);
    }

    @Override
    public MadDog copy() {
        return new MadDog(this);
    }
}

enum MadDogCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        // TRUE if came under your control this turn
        return permanent != null && !permanent.wasControlledFromStartOfControllerTurn();
    }
}
