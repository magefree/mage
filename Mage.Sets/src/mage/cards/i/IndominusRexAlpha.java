package mage.cards.i;

import java.util.*;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.AbilityCounter;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetDiscard;

/**
 *
 * @author jimga150
 */
public final class IndominusRexAlpha extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(null);

    public IndominusRexAlpha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U/B}{U/B}{G}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // As Indominus Rex, Alpha enters the battlefield, discard any number of creature cards. It enters with a
        // flying counter on it if a card discarded this way has flying. The same is true for first strike,
        // double strike, deathtouch, hexproof, haste, indestructible, lifelink, menace, reach, trample, and vigilance.
        this.addAbility(new AsEntersBattlefieldAbility(new IndominusRexAlphaCountersEffect()));

        // When Indominus Rex enters the battlefield, draw a card for each counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(xValue)));
    }

    private IndominusRexAlpha(final IndominusRexAlpha card) {
        super(card);
    }

    @Override
    public IndominusRexAlpha copy() {
        return new IndominusRexAlpha(this);
    }
}

// Based on MindMaggotsEffect
class IndominusRexAlphaCountersEffect extends OneShotEffect {

    private static final List<CounterType> copyableCounters = Arrays.asList(
            CounterType.FLYING,
            CounterType.FIRST_STRIKE,
            CounterType.DOUBLE_STRIKE,
            CounterType.DEATHTOUCH,
            CounterType.HEXPROOF,
            CounterType.HASTE,
            CounterType.INDESTRUCTIBLE,
            CounterType.LIFELINK,
            CounterType.MENACE,
            CounterType.REACH,
            CounterType.TRAMPLE,
            CounterType.VIGILANCE
    );

    IndominusRexAlphaCountersEffect() {
        // AI will discard whole hand if this is set to a beneficial outcome without custom logic, which is bad
        // practice. so we will just ask it to not discard anything
        super(Outcome.AIDontUseIt);

        this.staticText = "discard any number of creature cards. It enters with a " + getFirstChoiceAsString() +
                " counter on it if a card discarded this way has " + getFirstChoiceAsString()
                + ". The same is true for " + getOtherChoicesAsString() + ".";
        for (CounterType counterType : copyableCounters) {
            Counter counter = counterType.createInstance();
            if (!(counter instanceof AbilityCounter)) {
                throw new IllegalArgumentException(counterType + " is not an ability counter");
            }
        }
    }

    protected String getFirstChoiceAsString(){
        return copyableCounters.toArray(new CounterType[0])[0].getName();
    }

    protected String getOtherChoicesAsString(){
        StringBuilder sb = new StringBuilder();
        CounterType[] choicesArray = copyableCounters.toArray(new CounterType[0]);

        // Skip first counter name - flying
        for (int i = 1; i < copyableCounters.size(); i++){
            String separator = ", ";
            if (i == copyableCounters.size()-2){
                separator = ", and ";
            } else if (i == copyableCounters.size()-1){
                separator = "";
            }
            sb.append(choicesArray[i].getName()).append(separator);
        }
        return sb.toString();
    }

    private IndominusRexAlphaCountersEffect(final IndominusRexAlphaCountersEffect effect) {
        super(effect);
    }

    @Override
    public IndominusRexAlphaCountersEffect copy() {
        return new IndominusRexAlphaCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // EntersBattlefieldAbility is static, see AddCountersSourceEffect
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent == null) {
            return true;
        }

        TargetCard target = new TargetDiscard(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURE, controller.getId());
        controller.choose(outcome, controller.getHand(), target, source, game);
        List<UUID> chosenTargets = target.getTargets();

        // the basic event is the EntersBattlefieldEvent, so use already applied replacement effects from that event
        List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");

        boolean addedCounters = false;

        for (CounterType counterType : copyableCounters) {

            Counter counter = counterType.createInstance();
            Ability abilityToCopy = ((AbilityCounter)(counter)).getAbility();
            Class<? extends Ability> abilityClass = abilityToCopy.getClass();

            boolean addedCounterType = false;

            for (UUID targetId : chosenTargets) {

                Card card = game.getCard(targetId);
                if (card == null){
                    continue;
                }

                for (Ability ability : card.getAbilities(game)) {
                    if (abilityClass.isInstance(ability)){
                        permanent.addCounters(counter, source.getControllerId(), source, game, appliedEffects);
                        addedCounterType = true;
                        break;
                    }
                }

                if (addedCounterType){
                    addedCounters = true;
                    break;
                }

            }

        }
        controller.discard(new CardsImpl(target.getTargets()), false, source, game);
        return addedCounters;
    }
}
