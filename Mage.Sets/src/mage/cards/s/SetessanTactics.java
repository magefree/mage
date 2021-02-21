package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.abilityword.StriveAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SetessanTactics extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SetessanTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Strive - Setessan Tactics costs G more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{G}"));

        // Until end of turn, any number of target creatures each get +1/+1 and gain "T: This creature fights another target creature."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        effect.setText("Until end of turn, any number of target creatures each get +1/+1");
        this.getSpellAbility().addEffect(effect);
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new FightTargetSourceEffect(), new TapSourceCost());
        gainedAbility.addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(gainedAbility, Duration.EndOfTurn,
                "and gain \"{T}: This creature fights another target creature.\""));
    }

    private SetessanTactics(final SetessanTactics card) {
        super(card);
    }

    @Override
    public SetessanTactics copy() {
        return new SetessanTactics(this);
    }
}
