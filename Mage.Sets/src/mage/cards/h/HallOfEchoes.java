package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ruleModifying.LegendRuleDoesntApplyEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.functions.EmptyCopyApplier;

/**
 *
 * @author muz
 */
public final class HallOfEchoes extends CardImpl {

    public HallOfEchoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {5}: This land becomes a copy of target creature you control until end of turn. The "legend rule" doesn't apply to permanents you control this turn.
        Ability ability = new SimpleActivatedAbility(new HallOfEchoesCopyEffect(), new GenericManaCost(5));
        ability.addEffect(new LegendRuleDoesntApplyEffect(StaticFilters.FILTER_CONTROLLED_PERMANENTS)
                .setDuration(Duration.EndOfTurn)
                .setText("The \"legend rule\" doesn't apply to permanents you control this turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private HallOfEchoes(final HallOfEchoes card) {
        super(card);
    }

    @Override
    public HallOfEchoes copy() {
        return new HallOfEchoes(this);
    }
}

class HallOfEchoesCopyEffect extends OneShotEffect {

    HallOfEchoesCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "this land becomes a copy of target creature you control until end of turn";
    }

    private HallOfEchoesCopyEffect(final HallOfEchoesCopyEffect effect) {
        super(effect);
    }

    @Override
    public HallOfEchoesCopyEffect copy() {
        return new HallOfEchoesCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent == null || copyFromPermanent == null) {
            return false;
        }
        game.copyPermanent(Duration.EndOfTurn, copyFromPermanent, sourcePermanent.getId(), source, new EmptyCopyApplier());
        return true;
    }
}
