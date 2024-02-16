package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MalametBattleGlyph extends CardImpl {

    public MalametBattleGlyph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Choose target creature you control and target creature you don't control. If the creature you control entered the battlefield this turn, put a +1/+1 counter on it. Then those creatures fight each other.
        this.getSpellAbility().addEffect(new MalametBattleGlyphEffect());
        this.getSpellAbility().addEffect(new FightTargetsEffect().setText("Then those creatures fight each other"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private MalametBattleGlyph(final MalametBattleGlyph card) {
        super(card);
    }

    @Override
    public MalametBattleGlyph copy() {
        return new MalametBattleGlyph(this);
    }
}

class MalametBattleGlyphEffect extends OneShotEffect {

    MalametBattleGlyphEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature you control and target creature you don't control. " +
                "If the creature you control entered the battlefield this turn, put a +1/+1 counter on it";
    }

    private MalametBattleGlyphEffect(final MalametBattleGlyphEffect effect) {
        super(effect);
    }

    @Override
    public MalametBattleGlyphEffect copy() {
        return new MalametBattleGlyphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null
                && permanent.getTurnsOnBattlefield() == 0
                && permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
    }
}
