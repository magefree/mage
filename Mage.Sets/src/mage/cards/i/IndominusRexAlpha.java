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
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.AbilityCounter;
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

    private static final List<Ability> copyableAbilities = Arrays.asList(
            FlyingAbility.getInstance(),
            FirstStrikeAbility.getInstance(),
            DoubleStrikeAbility.getInstance(),
            DeathtouchAbility.getInstance(),
            HexproofAbility.getInstance(),  // Hexproof has a number of variants that will be handled separately
            HasteAbility.getInstance(),
            IndestructibleAbility.getInstance(),
            LifelinkAbility.getInstance(),
            new MenaceAbility(),
            ReachAbility.getInstance(),
            TrampleAbility.getInstance(),
            VigilanceAbility.getInstance()
    );

    IndominusRexAlphaCountersEffect() {
        // AI will discard whole hand if this is set to a beneficial outcome without custom logic, which is bad
        // practice. so we will just ask it to not discard anything
        super(Outcome.AIDontUseIt);

        this.staticText = "discard any number of creature cards. It enters with a flying counter on it if a card " +
                "discarded this way has flying. The same is true for first strike, double strike, deathtouch, " +
                "hexproof, haste, indestructible, lifelink, menace, reach, trample, and vigilance.";
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

        // Must discard before checking abilities and adding counters
        // from MTG judge chat at https://chat.magicjudges.org/mtgrules/
        //
        // jimga150: If Yixlid Jailer is on the battlefield, will discarding cards with Indominus cause indominus to
        // get no counters from its ability?
        //
        // R0b_: The discarded card won't have any abilities in the graveyard and Indominus won't get a counter from it
        controller.discard(new CardsImpl(target.getTargets()), false, source, game);

        //allow cards to move to graveyard before checking for abilities
        game.getState().processAction(game);

        // the basic event is the EntersBattlefieldEvent, so use already applied replacement effects from that event
        List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");

        ArrayList<Ability> abilitiesToAdd = new ArrayList<>();

        for (Ability abilityToCopy : copyableAbilities) {
            
            for (UUID targetId : chosenTargets) {

                Card card = game.getCard(targetId);
                if (card == null){
                    continue;
                }

                for (Ability ability : card.getAbilities(game)) {
                    if (abilitiesToAdd.stream().anyMatch(a -> a.getClass().isInstance(ability))){
                        continue;
                    }
                    if (abilityToCopy.getClass().isInstance(ability)){
                        abilitiesToAdd.add(ability);
                    } else if (ability instanceof HexproofBaseAbility) {
                        // Any subclass of HexproofBaseAbility gets added too--not just instances of HexproofAbility
                        abilitiesToAdd.add(ability);
                    }
                }

            }

        }

        for (Ability abilityToCopy : abilitiesToAdd) {
            permanent.addCounters(new AbilityCounter(abilityToCopy, 1), source.getControllerId(), source, game, appliedEffects);
        }
        return !abilitiesToAdd.isEmpty();
    }
}
