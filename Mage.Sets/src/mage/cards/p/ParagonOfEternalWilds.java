
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ParagonOfEternalWilds extends CardImpl {
    
    private static final FilterCreaturePermanent filterGreen = new FilterCreaturePermanent("green creatures you control");
    private static final FilterControlledCreaturePermanent filterGreen2 = new FilterControlledCreaturePermanent("another target green creature you control");

    static {
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterGreen.add(TargetController.YOU.getControllerPredicate());
        filterGreen2.add(new ColorPredicate(ObjectColor.GREEN));        
        filterGreen2.add(AnotherPredicate.instance);
    }
    
    public ParagonOfEternalWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other green creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filterGreen, true)));
        // {G}, {t}: Another target green creature you control gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(TrampleAbility.getInstance(),Duration.EndOfTurn), new ManaCostsImpl<>("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent(filterGreen2));
        this.addAbility(ability);
    }

    private ParagonOfEternalWilds(final ParagonOfEternalWilds card) {
        super(card);
    }

    @Override
    public ParagonOfEternalWilds copy() {
        return new ParagonOfEternalWilds(this);
    }
}
