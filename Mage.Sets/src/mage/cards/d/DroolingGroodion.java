
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

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
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DroolingGroodionEffect(), new ManaCostsImpl<>("{2}{B}{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        TargetCreaturePermanent target = new TargetCreaturePermanent(new FilterCreaturePermanent("creature (first target)"));
        target.setTargetTag(1);
        ability.addTarget(target);

        FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature (second target");
        filter.add(new AnotherTargetPredicate(2));
        target = new TargetCreaturePermanent(filter);
        target.setTargetTag(2);
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

class DroolingGroodionEffect extends ContinuousEffectImpl {

    public DroolingGroodionEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "Target creature gets +2/+2 until end of turn. Another target creature gets -2/-2 until end of turn";
    }

    public DroolingGroodionEffect(final DroolingGroodionEffect effect) {
        super(effect);
    }

    @Override
    public DroolingGroodionEffect copy() {
        return new DroolingGroodionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.addPower(2);
            permanent.addToughness(2);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.addPower(-2);
            permanent.addToughness(-2);
        }
        return true;
    }
}
