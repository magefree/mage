
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class HivisOfTheScale extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent();
    private static final FilterPermanent filterDragon = new FilterPermanent();
    private static final String rule = "Gain control of target Dragon for as long as you control Hivis and Hivis remains tapped.";
    
    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(TargetController.YOU.getControllerPredicate());
        filterDragon.add(SubType.DRAGON.getPredicate());
    }

    public HivisOfTheScale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // You may choose not to untap Hivis of the Scale during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        
        // {tap}: Gain control of target Dragon for as long as you control Hivis and Hivis remains tapped.
        Condition condition = new SourceMatchesFilterCondition(filter);
        Effect effect = new ConditionalContinuousEffect(new GainControlTargetEffect(Duration.Custom), condition, rule);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetPermanent(filterDragon));
        this.addAbility(ability);
        
    }

    private HivisOfTheScale(final HivisOfTheScale card) {
        super(card);
    }

    @Override
    public HivisOfTheScale copy() {
        return new HivisOfTheScale(this);
    }
}
