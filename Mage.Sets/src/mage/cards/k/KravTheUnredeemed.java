
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class KravTheUnredeemed extends CardImpl {

    public KravTheUnredeemed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Regna, the Redeemer (When this creature enters the battlefield, target player may put Regna into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Regna, the Redeemer", true));

        // {B}, Sacrifice X creatures: Target player draws X cards and gains X life. Put X +1/+1 counters on Krav, the Unredeemed.
        Ability ability = new SimpleActivatedAbility(new KravTheUnredeemedEffect(), new ManaCostsImpl<>("{B}"));
        ability.addTarget(new TargetPlayer());
        ability.addCost(new SacrificeXTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE));
        this.addAbility(ability);
    }

    private KravTheUnredeemed(final KravTheUnredeemed card) {
        super(card);
    }

    @Override
    public KravTheUnredeemed copy() {
        return new KravTheUnredeemed(this);
    }
}

class KravTheUnredeemedEffect extends OneShotEffect {

    KravTheUnredeemedEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player draws X cards and gains X life. Put X +1/+1 counters on {this}";
    }

    KravTheUnredeemedEffect(final KravTheUnredeemedEffect effect) {
        super(effect);
    }

    @Override
    public KravTheUnredeemedEffect copy() {
        return new KravTheUnredeemedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = GetXValue.instance.calculate(game, source, this);
        new DrawCardTargetEffect(xValue).apply(game, source);
        new GainLifeTargetEffect(xValue).apply(game, source);
        new AddCountersSourceEffect(CounterType.P1P1.createInstance(xValue)).apply(game, source);
        return true;
    }
}
