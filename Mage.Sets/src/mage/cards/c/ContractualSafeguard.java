package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.RandomUtil;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ContractualSafeguard extends CardImpl {

    public ContractualSafeguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Addendum — If you cast this spell during your main phase, put a shield counter on a creature you control.
        this.getSpellAbility().addEffect(new ContractualSafeguardFirstEffect());
        this.getSpellAbility().setAbilityWord(AbilityWord.ADDENDUM);

        // Choose a kind of counter on a creature you control. Put a counter of that kind on each other creature you control.
        this.getSpellAbility().addEffect(new ContractualSafeguardSecondEffect());
    }

    private ContractualSafeguard(final ContractualSafeguard card) {
        super(card);
    }

    @Override
    public ContractualSafeguard copy() {
        return new ContractualSafeguard(this);
    }
}

class ContractualSafeguardFirstEffect extends OneShotEffect {

    ContractualSafeguardFirstEffect() {
        super(Outcome.Benefit);
        staticText = "if you cast this spell during your main phase, put a shield counter on a creature you control";
    }

    private ContractualSafeguardFirstEffect(final ContractualSafeguardFirstEffect effect) {
        super(effect);
    }

    @Override
    public ContractualSafeguardFirstEffect copy() {
        return new ContractualSafeguardFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!AddendumCondition.instance.apply(game, source)) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source, game, 1
        )) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.withChooseHint("to give a shield counter").setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.addCounters(CounterType.SHIELD.createInstance(), source, game);
    }
}

class ContractualSafeguardSecondEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    ContractualSafeguardSecondEffect() {
        super(Outcome.Benefit);
        staticText = "choose a kind of counter on a creature you control. " +
                "Put a counter of that kind on each other creature you control";
        concatPrefix = "<br>";
    }

    private ContractualSafeguardSecondEffect(final ContractualSafeguardSecondEffect effect) {
        super(effect);
    }

    @Override
    public ContractualSafeguardSecondEffect copy() {
        return new ContractualSafeguardSecondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(filter, source, game, 1)) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Set<String> counterTypes = permanent.getCounters(game).keySet();
        String chosenType;
        switch (counterTypes.size()) {
            case 0:
                return false;
            case 1:
                chosenType = RandomUtil.randomFromCollection(counterTypes);
                break;
            case 2:
                Iterator<String> iterator = counterTypes.iterator();
                String type1 = iterator.next();
                String type2 = iterator.next();
                chosenType = player.chooseUse(
                        outcome, "Choose which kind of counter to add to each other creature you control",
                        null, type1, type2, source, game
                ) ? type1 : type2;
                break;
            default:
                Choice choice = new ChoiceImpl(true);
                choice.setMessage("Choose a kind of counter to add to each other creature you control");
                choice.setChoices(counterTypes);
                player.choose(outcome, choice, game);
                chosenType = choice.getChoice();
        }
        CounterType counterType = CounterType.findByName(chosenType);
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (!creature.getId().equals(permanent.getId())) {
                creature.addCounters(counterType.createInstance(), source, game);
            }
        }
        return true;
    }
}
