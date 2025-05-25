package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author jimga150
 */
public final class SeasonOfGathering extends CardImpl {

    public SeasonOfGathering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Choose up to five {P} worth of modes. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMaxPawPrints(5);
        this.getSpellAbility().getModes().setMinModes(0);
        this.getSpellAbility().getModes().setMaxModes(5);
        this.getSpellAbility().getModes().setMayChooseSameModeMoreThanOnce(true);

        // {P} -- Put a +1/+1 counter on a creature you control. It gains vigilance and trample until end of turn.
        this.getSpellAbility().addEffect(new SeasonOfGatheringCounterEffect());
        this.getSpellAbility().getModes().getMode().withPawPrintValue(1);

        // {P}{P} -- Choose artifact or enchantment. Destroy all permanents of the chosen type.
        Mode mode2 = new Mode(new SeasonOfGatheringRemovalEffect());
        this.getSpellAbility().addMode(mode2.withPawPrintValue(2));

        // {P}{P}{P} -- Draw cards equal to the greatest power among creatures you control.
        Mode mode3 = new Mode(
                new DrawCardSourceControllerEffect(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES)
                        .setText("Draw cards equal to the greatest power among creatures you control.")
        );
        this.getSpellAbility().addMode(mode3.withPawPrintValue(3));
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint());
    }

    private SeasonOfGathering(final SeasonOfGathering card) {
        super(card);
    }

    @Override
    public SeasonOfGathering copy() {
        return new SeasonOfGathering(this);
    }
}

// Based on KaylasCommandCounterEffect
class SeasonOfGatheringCounterEffect extends OneShotEffect {

    SeasonOfGatheringCounterEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Put a +1/+1 counter on a creature you control. It gains vigilance and trample until end of turn.";
    }

    private SeasonOfGatheringCounterEffect(final SeasonOfGatheringCounterEffect effect) {
        super(effect);
    }

    @Override
    public SeasonOfGatheringCounterEffect copy() {
        return new SeasonOfGatheringCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
        target.withNotTarget(true);
        controller.chooseTarget(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        GainAbilityTargetEffect effect = new GainAbilityTargetEffect(VigilanceAbility.getInstance());
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect, source);
        GainAbilityTargetEffect effect2 = new GainAbilityTargetEffect(TrampleAbility.getInstance());
        effect2.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect2, source);
        return true;
    }
}

// Based on KindredDominanceEffect and TurnaboutEffect
class SeasonOfGatheringRemovalEffect extends OneShotEffect {

    private static final Set<String> choice = new HashSet<>();

    static {
        choice.add(CardType.ARTIFACT.toString());
        choice.add(CardType.ENCHANTMENT.toString());
    }

    SeasonOfGatheringRemovalEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Choose artifact or enchantment. Destroy all permanents of the chosen type.";
    }

    private SeasonOfGatheringRemovalEffect(final SeasonOfGatheringRemovalEffect effect) {
        super(effect);
    }

    @Override
    public SeasonOfGatheringRemovalEffect copy() {
        return new SeasonOfGatheringRemovalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Choice choiceImpl = new ChoiceImpl(true);
        choiceImpl.setMessage("Choose card type to destroy");
        choiceImpl.setChoices(choice);
        if (!controller.choose(Outcome.Neutral, choiceImpl, game)) {
            return false;
        }
        FilterPermanent filter;
        switch (choiceImpl.getChoice()) {
            case "Artifact":
                filter = StaticFilters.FILTER_PERMANENT_ARTIFACT;
                break;
            case "Enchantment":
                filter = StaticFilters.FILTER_PERMANENT_ENCHANTMENT;
                break;
            default:
                throw new IllegalArgumentException("Choice is required");
        }
        game.informPlayers(controller.getLogName() + " has chosen " + choiceImpl.getChoiceKey());
        return new DestroyAllEffect(filter).apply(game, source);
    }
}
