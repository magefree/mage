package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class DroolingGroodion extends CardImpl {

    public DroolingGroodion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {2}{B}{G}, Sacrifice a creature: Target creature gets +2/+2 until end of turn. Another target creature gets -2/-2 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(2, 2), new ManaCostsImpl<>("{2}{B}{G}"));
        ability.addEffect(new BoostTargetEffect(-2, -2).setTargetPointer(new SecondTargetPointer()));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        TargetCreaturePermanent target = new TargetCreaturePermanent();
        target.setTargetTag(1);
        target.withChooseHint("gets +2/+2");
        ability.addTarget(target);

        target = new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2);
        target.setTargetTag(2);
        target.withChooseHint("gets -2/-2");
        ability.addTarget(target);

        this.addAbility(ability);
    }

    private DroolingGroodion(final DroolingGroodion card) {
        super(card);
    }

    @Override
    public DroolingGroodion copy() {
        return new DroolingGroodion(this);
    }
}
