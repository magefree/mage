
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
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
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
public final class ParagonOfOpenGraves extends CardImpl {

    private static final FilterCreaturePermanent filterCreatures = new FilterCreaturePermanent("black creatures");
    private static final FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("another target black creature you control");

    static {
        filterCreatures.add(new ColorPredicate(ObjectColor.BLACK));
        filterCreature.add(AnotherPredicate.instance);
        filterCreature.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public ParagonOfOpenGraves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other black creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterCreatures, true)));

        // {2}{B}, {T}: Another target black creature you control gains deathtouch until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent(filterCreature));
        this.addAbility(ability);
    }

    private ParagonOfOpenGraves(final ParagonOfOpenGraves card) {
        super(card);
    }

    @Override
    public ParagonOfOpenGraves copy() {
        return new ParagonOfOpenGraves(this);
    }
}
