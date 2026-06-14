package mage.cards.g;

import mage.abilities.Mode;
import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class GoNuts extends CardImpl {

    private static final FilterCreaturePermanent filter
        = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public GoNuts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Teamwork 3
        this.addAbility(new TeamworkAbility(3));

        // Choose one. If this spell was cast using teamwork, choose both instead.
        this.getSpellAbility().getModes().setChooseText(
            "Choose one. If this spell was cast using teamwork, choose both instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(2, TeamworkCondition.instance);

        // * Put a +1/+1 counter on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Target creature you control fights target creature an opponent controls.
        Mode mode = new Mode(new FightTargetsEffect());
        mode.addTarget(new TargetControlledCreaturePermanent());
        mode.addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    private GoNuts(final GoNuts card) {
        super(card);
    }

    @Override
    public GoNuts copy() {
        return new GoNuts(this);
    }
}
