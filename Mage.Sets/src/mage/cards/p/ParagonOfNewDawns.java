
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ParagonOfNewDawns extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white creatures");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Another white creature you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(AnotherPredicate.instance);
        filter2.add(new ColorPredicate(ObjectColor.WHITE));
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public ParagonOfNewDawns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other white creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1,1, Duration.WhileOnBattlefield, filter, true)));
        // {W}, {T}: Another target white creature you control gains vigilance until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability);
    }

    private ParagonOfNewDawns(final ParagonOfNewDawns card) {
        super(card);
    }

    @Override
    public ParagonOfNewDawns copy() {
        return new ParagonOfNewDawns(this);
    }
}
