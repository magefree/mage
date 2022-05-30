package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XTargetsAdjuster;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class DeepwoodElder extends CardImpl {

    public DeepwoodElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");

        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {X}{G}{G}, {tap}, Discard a card: X target lands become Forests until end of turn.
        Ability ability = new SimpleActivatedAbility(new DeepwoodElderEffect(), new ManaCostsImpl<>("{X}{G}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_LANDS));
        ability.setTargetAdjuster(XTargetsAdjuster.instance);
        this.addAbility(ability);
    }

    private DeepwoodElder(final DeepwoodElder card) {
        super(card);
    }

    @Override
    public DeepwoodElder copy() {
        return new DeepwoodElder(this);
    }
}

class DeepwoodElderEffect extends OneShotEffect {

    DeepwoodElderEffect() {
        super(Outcome.LoseAbility);
        this.staticText = "X target lands become Forests until end of turn";
    }

    DeepwoodElderEffect(final DeepwoodElderEffect effect) {
        super(effect);
    }

    @Override
    public DeepwoodElderEffect copy() {
        return new DeepwoodElderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Target target : source.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    ContinuousEffect effect = new BecomesBasicLandTargetEffect(Duration.EndOfTurn, SubType.FOREST);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
            }
        }
        return true;
    }
}
