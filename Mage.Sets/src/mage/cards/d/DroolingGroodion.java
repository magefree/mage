package mage.cards.d;

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
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2;

/**
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
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.addEffect(new BoostTargetEffect(-2, -2).setTargetPointer(new SecondTargetPointer()));
        ability.addTarget(new TargetCreaturePermanent().setTargetTag(1).withChooseHint("gets +2/+2"));
        ability.addTarget(new TargetPermanent(FILTER_ANOTHER_CREATURE_TARGET_2).setTargetTag(2).withChooseHint("gets -2/-2"));
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
