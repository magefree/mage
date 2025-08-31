package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TromellSeymoursButler extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public TromellSeymoursButler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Each other nontoken creature you control enters with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new EntersWithCountersControlledEffect(
                filter, CounterType.P1P1.createInstance(), true
        )));

        // {1}, {T}: Proliferate X times, where X is the number of nontoken creatures you control that entered this turn.
        Ability ability = new SimpleActivatedAbility(new TromellSeymoursButlerEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(TromellSeymoursButlerEffect.getHint()));
    }

    private TromellSeymoursButler(final TromellSeymoursButler card) {
        super(card);
    }

    @Override
    public TromellSeymoursButler copy() {
        return new TromellSeymoursButler(this);
    }
}

class TromellSeymoursButlerEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(EnteredThisTurnPredicate.instance);
    }

    private static final Hint hint = new ValueHint(
            "Nontoken creatures you control that entered this turn", new PermanentsOnBattlefieldCount(filter)
    );

    TromellSeymoursButlerEffect() {
        super(Outcome.Benefit);
        staticText = "proliferate X times, where X is the number of " +
                "nontoken creatures you control that entered this turn";
    }

    private TromellSeymoursButlerEffect(final TromellSeymoursButlerEffect effect) {
        super(effect);
    }

    @Override
    public TromellSeymoursButlerEffect copy() {
        return new TromellSeymoursButlerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        if (count < 1) {
            return false;
        }
        Effect effect = new ProliferateEffect();
        for (int i = 0; i < count; i++) {
            effect.apply(game, source);
        }
        return true;
    }

    public static Hint getHint() {
        return hint;
    }
}
