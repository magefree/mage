
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class UlrichUncontestedAlpha extends CardImpl {

    public UlrichUncontestedAlpha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.color.setRed(true);
        this.color.setGreen(true);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        // Whenever this creature transforms into Ulrich, Uncontested Alpha, you may have it fight target non-Werewolf creature you don't control.
        this.addAbility(new UlrichUncontestedAlphaAbility());

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Ulrich, Uncontested Alpha.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public UlrichUncontestedAlpha(final UlrichUncontestedAlpha card) {
        super(card);
    }

    @Override
    public UlrichUncontestedAlpha copy() {
        return new UlrichUncontestedAlpha(this);
    }
}

class UlrichUncontestedAlphaAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Werewolf creature you don't control");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.WEREWOLF)));
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public UlrichUncontestedAlphaAbility() {
        super(Zone.BATTLEFIELD, new FightTargetSourceEffect(), true);
        Target target = new TargetCreaturePermanent(filter);
        this.addTarget(target);
    }

    public UlrichUncontestedAlphaAbility(final UlrichUncontestedAlphaAbility ability) {
        super(ability);
    }

    @Override
    public UlrichUncontestedAlphaAbility copy() {
        return new UlrichUncontestedAlphaAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(sourceId)) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && permanent.isTransformed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature transforms into Ulrich, Uncontested Alpha, you may have it fight target non-Werewolf creature you don't control.";
    }
}
