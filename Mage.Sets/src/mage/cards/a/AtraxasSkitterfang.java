package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AtraxasSkitterfang extends CardImpl {

    public AtraxasSkitterfang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Atraxa's Skitterfang enters the battlefield with three oil counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance(3)),
                "with three oil counters on it"
        ));

        // At the beginning of combat on your turn, you may remove an oil counter from Atraxa's Skitterfang. When you do, target creature you control gains your choice of flying, vigilance, deathtouch, or lifelink until end of turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new AtraxasSkitterfangEffect(), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DoWhenCostPaid(
                ability,
                new RemoveCountersSourceCost(CounterType.OIL.createInstance()),
                "Remove an oil counter?"
        ), TargetController.YOU, false));
    }

    private AtraxasSkitterfang(final AtraxasSkitterfang card) {
        super(card);
    }

    @Override
    public AtraxasSkitterfang copy() {
        return new AtraxasSkitterfang(this);
    }
}

class AtraxasSkitterfangEffect extends OneShotEffect {

    private static final Map<String, Ability> abilityMap = new HashMap<>();

    static {
        abilityMap.put("Flying", FlyingAbility.getInstance());
        abilityMap.put("Vigilance", VigilanceAbility.getInstance());
        abilityMap.put("Deathtouch", DeathtouchAbility.getInstance());
        abilityMap.put("Lifelink", LifelinkAbility.getInstance());
    }

    AtraxasSkitterfangEffect() {
        super(Outcome.Benefit);
        staticText = "target creature you control gains your choice " +
                "of flying, vigilance, deathtouch, or lifelink until end of turn";
    }

    private AtraxasSkitterfangEffect(final AtraxasSkitterfangEffect effect) {
        super(effect);
    }

    @Override
    public AtraxasSkitterfangEffect copy() {
        return new AtraxasSkitterfangEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose an ability");
        choice.setChoices(new HashSet<>(abilityMap.keySet()));
        player.choose(outcome, choice, game);
        Ability ability = abilityMap.getOrDefault(choice.getChoice(), null);
        if (ability != null) {
            game.addEffect(new GainAbilityTargetEffect(ability, Duration.EndOfTurn), source);
        }
        return true;
    }
}
