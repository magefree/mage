
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth

 */
public final class MindwrackLiege extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creatures you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("red creatures you control");
    private static final FilterCreatureCard filter3 = new FilterCreatureCard("a blue or red creature card");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(new ColorPredicate(ObjectColor.RED));
        filter2.add(TargetController.YOU.getControllerPredicate());
        filter3.add(Predicates.or(new ColorPredicate(ObjectColor.BLUE), new ColorPredicate(ObjectColor.RED)));
    }

    public MindwrackLiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U/R}{U/R}{U/R}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Other blue creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        // Other red creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter2, true)));

        // {UR}{UR}{UR}{UR}: You may put a blue or red creature card from your hand onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutCardFromHandOntoBattlefieldEffect(filter3), new ManaCostsImpl<>("{U/R}{U/R}{U/R}{U/R}")));
    }

    private MindwrackLiege(final MindwrackLiege card) {
        super(card);
    }

    @Override
    public MindwrackLiege copy() {
        return new MindwrackLiege(this);
    }
}
