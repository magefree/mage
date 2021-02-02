
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public final class StoneshockGiant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures without flying your opponents control");
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public StoneshockGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // {6}{R}{R}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{6}{R}{R}",3));
        // When Stoneshock Giant becomes monstrous, creatures without flying your opponents control can't block this turn.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new CantBlockAllEffect(filter, Duration.EndOfTurn)));
    }

    private StoneshockGiant(final StoneshockGiant card) {
        super(card);
    }

    @Override
    public StoneshockGiant copy() {
        return new StoneshockGiant(this);
    }
}
