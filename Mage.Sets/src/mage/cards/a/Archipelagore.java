package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourceMutatedCount;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Archipelagore extends CardImpl {

    public Archipelagore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Mutate {5}{U}
        this.addAbility(new MutateAbility(this, "{5}{U}"));

        // Whenever this creature mutates, tap up to X target creatures, where X is the number of times this creature has mutated. Those creatures don't untap during their controller's next untap step.
        Ability ability = new MutatesSourceTriggeredAbility(new TapTargetEffect(
                "tap up to X target creatures, where X is the number of times this creature has mutated."
        ));
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("Those creatures"));
        ability.setTargetAdjuster(ArchipelagoreAdjuster.instance);
        this.addAbility(ability);
    }

    private Archipelagore(final Archipelagore card) {
        super(card);
    }

    @Override
    public Archipelagore copy() {
        return new Archipelagore(this);
    }
}

enum ArchipelagoreAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int mutateCount = SourceMutatedCount.instance.calculate(game, ability, null);
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(0, mutateCount));
    }
}