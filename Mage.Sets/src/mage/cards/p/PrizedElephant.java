
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class PrizedElephant extends CardImpl {
    
    private static final String rule = "{this} gets +1/+1 as long as you control a Forest";
    private static final FilterLandPermanent filter = new FilterLandPermanent("a Forest");
    
    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public PrizedElephant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.ELEPHANT);

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Prized Elephant gets +1/+1 as long as you control a Forest.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter), rule)));
        
        // {G}: Prized Elephant gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{G}"));
        this.addAbility(ability);
    }

    private PrizedElephant(final PrizedElephant card) {
        super(card);
    }

    @Override
    public PrizedElephant copy() {
        return new PrizedElephant(this);
    }
}
