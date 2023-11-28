
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author Plopman
 */
public final class Biovisionary extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("if you control four or more creatures named Biovisionary");
    static{
        filter.add(new NamePredicate("Biovisionary"));
    }
           
    public Biovisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{U}");
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        //At the beginning of the end step, if you control four or more creatures named Biovisionary, you win the game.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new WinGameSourceControllerEffect(), 
                TargetController.NEXT,
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 3),
                false
        ).addHint(new ValueHint("Creatures you control named Biovisionary", new PermanentsOnBattlefieldCount(filter))));
    }

    private Biovisionary(final Biovisionary card) {
        super(card);
    }

    @Override
    public Biovisionary copy() {
        return new Biovisionary(this);
    }
}
