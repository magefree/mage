package mage.cards.r;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.token.BirdToken;
import mage.game.permanent.token.MonasteryMentorToken;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.SpellsCastWatcher;

/**
 * @author balazskristof
 */
public final class RallyTheMonastery extends CardImpl {

    private static final Hint hint = new ConditionHint(
        RallyTheMonasteryCondition.instance, "You've cast a spell this turn"
    );

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public RallyTheMonastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // This spell costs {2} less to cast if you've cast another spell this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(2, RallyTheMonasteryCondition.instance).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true).addHint(hint));

        // Choose one —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // • Create two 1/1 white Monk creature tokens with prowess.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new MonasteryMentorToken(), 2));
        // • Up to two target creatures you control each get +2/+2 until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(2, 2));
        mode.addTarget(new TargetControlledCreaturePermanent(0, 2, StaticFilters.FILTER_CONTROLLED_CREATURES, false));
        this.getSpellAbility().getModes().addMode(mode);
        // • Destroy target creature with power 4 or greater.
        Mode mode2 = new Mode(new DestroyTargetEffect());
        mode2.addTarget(new TargetPermanent(filter));
        this.getSpellAbility().getModes().addMode(mode2);
    }

    private RallyTheMonastery(final RallyTheMonastery card) {
        super(card);
    }

    @Override
    public RallyTheMonastery copy() {
        return new RallyTheMonastery(this);
    }
}

enum RallyTheMonasteryCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        List<Spell> spells = watcher.getSpellsCastThisTurn(source.getControllerId());
        return spells != null && spells
                .stream()
                .filter(Objects::nonNull)
                .anyMatch(spell -> !spell.getSourceId().equals(source.getSourceId()));
    }

    @Override
    public String toString() {
        return "you've cast another spell this turn";
    }
}

