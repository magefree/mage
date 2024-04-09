package mage.cards.o;

import java.util.*;

import mage.MageInt;
import mage.abilities.*;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author jimga150
 */
public final class OwenGradyRaptorTrainer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DINOSAUR, "Dinosaur");

    public OwenGradyRaptorTrainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Partner with Blue, Loyal Raptor
        this.addAbility(new PartnerWithAbility("Blue, Loyal Raptor"));

        // {T}: Put your choice of a menace, trample, reach, or haste counter on target Dinosaur. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD, new OwenGradyRaptorTrainerEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        addAbility(ability);
    }

    private OwenGradyRaptorTrainer(final OwenGradyRaptorTrainer card) {
        super(card);
    }

    @Override
    public OwenGradyRaptorTrainer copy() {
        return new OwenGradyRaptorTrainer(this);
    }
}

// Based on AssaultronDominatorEffect
class OwenGradyRaptorTrainerEffect extends OneShotEffect {

    private static final Set<String> choices = new LinkedHashSet<>(Arrays.asList("Menace", "Trample", "Reach", "Haste"));

    OwenGradyRaptorTrainerEffect() {
        super(Outcome.BoostCreature);
        staticText = "put your choice of a " + getChoicesAsString() + " counter on target Dinosaur";
    }

    protected OwenGradyRaptorTrainerEffect(OwenGradyRaptorTrainerEffect effect) {
        super(effect);
    }

    protected String getChoicesAsString(){
        StringBuilder sb = new StringBuilder();
        String[] choicesArray = choices.toArray(new String[0]);
        for (int i = 0; i < choices.size(); i++){
            String separator = ", ";
            if (i == choices.size()-2){
                separator = ", or ";
            } else if (i == choices.size()-1){
                separator = "";
            }
            sb.append(choicesArray[i]).append(separator);
        }
        return sb.toString();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose " + getChoicesAsString() + " counter");
        choice.setChoices(choices);
        player.choose(outcome, choice, game);

        String chosen = choice.getChoice();

        CounterType counterType = null;
        for (CounterType c : CounterType.values()) {
            if (c.getName().equalsIgnoreCase(chosen)){
                counterType = c;
            }
        }

        if (counterType == null) {
            throw new IllegalArgumentException("Chosen counter type not found: " + chosen);
        }

        boolean applied = false;

        for (UUID target : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                applied |= permanent.addCounters(counterType.createInstance(), source, game);
            }
        }
        return applied;
    }

    @Override
    public OwenGradyRaptorTrainerEffect copy() {
        return new OwenGradyRaptorTrainerEffect(this);
    }
}
