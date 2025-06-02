package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public final class PhyrexianSplicer extends CardImpl {

    static final FilterCreaturePermanent filterLose = new FilterCreaturePermanent("creature with the chosen ability");
    private static final FilterCreaturePermanent filterGain = new FilterCreaturePermanent("another target creature");

    static {
        filterLose.add(Predicates.or(
                new AbilityPredicate(FlyingAbility.class),
                new AbilityPredicate(FirstStrikeAbility.class),
                new AbilityPredicate(TrampleAbility.class),
                new AbilityPredicate(ShadowAbility.class)
        ));
        filterLose.add(new AnotherTargetPredicate(1));

        filterGain.add(new AnotherTargetPredicate(2));
    }

    public PhyrexianSplicer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}, {T}, Choose flying, first strike, trample, or shadow: Until end of turn, target creature with the chosen ability loses it and another target creature gains it.
        Ability ability = new SimpleActivatedAbility(new PhyrexianSplicerEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PhyrexianSplicerChooseCost());
        ability.addTarget(new TargetPermanent(filterLose).withChooseHint("to lose ability").setTargetTag(1));
        ability.addTarget(new TargetPermanent(filterGain).withChooseHint("to gain ability").setTargetTag(2));
        ability.addHint(PhyrexianSplicerCardHint.instance);
        this.addAbility(ability);
    }

    private PhyrexianSplicer(final PhyrexianSplicer card) {
        super(card);
    }

    @Override
    public PhyrexianSplicer copy() {
        return new PhyrexianSplicer(this);
    }
}

class PhyrexianSplicerEffect extends OneShotEffect {

    PhyrexianSplicerEffect() {
        super(Outcome.LoseAbility);
        this.staticText = "Until end of turn, target creature with the chosen ability loses it and another target creature gains it.";
    }

    private PhyrexianSplicerEffect(final PhyrexianSplicerEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianSplicerEffect copy() {
        return new PhyrexianSplicerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Ability loseAbility = findChosenAbility(source);
        if (loseAbility == null) {
            return false;
        }

        // If the target which is having the ability removed does not have that ability during the resolution of this
        // effect, then this effect still grants the chosen ability. The reason is that the second target is still
        // legal even if the first one is not.
        // (2004-10-04)

        Permanent targetLose = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Permanent targetGain = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (targetGain == null) {
            return false;
        }

        // lose
        if (targetLose != null) {
            ContinuousEffect effect = new LoseAbilityTargetEffect(loseAbility, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(targetLose, game));
            game.addEffect(effect, source);
        }

        // gain
        ContinuousEffect effect = new GainAbilityTargetEffect(loseAbility, Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(targetGain, game));
        game.addEffect(effect, source);

        return true;
    }

    static Ability findChosenAbility(Ability source) {
        return CardUtil
                .castStream(source.getCosts().stream(), PhyrexianSplicerChooseCost.class)
                .map(PhyrexianSplicerChooseCost::getTargetedAbility)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}

class PhyrexianSplicerChooseCost extends CostImpl {

    private static final Map<String, Ability> allChoices = new LinkedHashMap<>();

    static {
        allChoices.put("Flying", FlyingAbility.getInstance());
        allChoices.put("First Strike", FirstStrikeAbility.getInstance());
        allChoices.put("Trample", TrampleAbility.getInstance());
        allChoices.put("Shadow", ShadowAbility.getInstance());
    }

    Ability targetedAbility = null;

    public PhyrexianSplicerChooseCost() {
        this.text = "Choose flying, first strike, trample, or shadow";
    }

    private PhyrexianSplicerChooseCost(final PhyrexianSplicerChooseCost cost) {
        super(cost);
        this.targetedAbility = cost.targetedAbility == null ? null : cost.targetedAbility.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        this.paid = false;
        this.targetedAbility = null;

        Permanent losePermanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Permanent gainPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (losePermanent == null || gainPermanent == null || controller == null) {
            return false;
        }

        // choose ability to lose
        Set<String> choices = allChoices.entrySet().stream()
                .filter(entry -> losePermanent.hasAbility(entry.getValue(), game))
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Ability chosenAbility;
        if (choices.size() == 1) {
            chosenAbility = allChoices.getOrDefault(choices.stream().findFirst().orElse(null), null);
        } else {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose ability to remove from " + losePermanent.getLogName() + " to " + gainPermanent.getLogName());
            choice.setChoices(choices);
            controller.choose(Outcome.LoseAbility, choice, game);
            chosenAbility = allChoices.getOrDefault(choice.getChoice(), null);
        }
        if (chosenAbility == null) {
            return false;
        }

        // all fine
        this.targetedAbility = chosenAbility;
        paid = true;

        // additional logs
        game.informPlayers(controller.getLogName() + " chosen ability to lose and gain: "
                + CardUtil.getTextWithFirstCharUpperCase(chosenAbility.getRule()));

        return true;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public PhyrexianSplicerChooseCost copy() {
        return new PhyrexianSplicerChooseCost(this);
    }

    Ability getTargetedAbility() {
        return this.targetedAbility;
    }
}

enum PhyrexianSplicerCardHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        // works on stack only
        if (ability instanceof StackAbility) {
            Ability loseAbility = PhyrexianSplicerEffect.findChosenAbility(((StackAbility) ability).getStackAbility());
            if (loseAbility != null) {
                return String.format("Chosen ability to lose and gain: " + CardUtil.getTextWithFirstCharUpperCase(loseAbility.getRule()));
            }
        }
        return "";
    }

    @Override
    public PhyrexianSplicerCardHint copy() {
        return this;
    }
}
