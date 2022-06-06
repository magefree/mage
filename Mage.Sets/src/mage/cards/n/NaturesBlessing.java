package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author L_J
 */
public final class NaturesBlessing extends CardImpl {

    public NaturesBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{W}");

        // {G}{W}, Discard a card: Put a +1/+1 counter on target creature or that creature gains banding, first strike, or trample.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NaturesBlessingEffect(), new ManaCostsImpl<>("{G}{W}"));
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NaturesBlessing(final NaturesBlessing card) {
        super(card);
    }

    @Override
    public NaturesBlessing copy() {
        return new NaturesBlessing(this);
    }
}

class NaturesBlessingEffect extends OneShotEffect {

    private static final Set<String> choices = new HashSet<>();
    private Ability gainedAbility;

    static {
        choices.add("+1/+1 counter");
        choices.add("Banding");
        choices.add("First strike");
        choices.add("Trample");
    }

    public NaturesBlessingEffect() {
        super(Outcome.AddAbility);
        this.staticText = "Put a +1/+1 counter on target creature or that creature gains banding, first strike, or trample";
    }

    public NaturesBlessingEffect(final NaturesBlessingEffect effect) {
        super(effect);
    }

    @Override
    public NaturesBlessingEffect copy() {
        return new NaturesBlessingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (controller != null && targetPermanent != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose one");
            choice.setChoices(choices);
            if (controller.choose(outcome, choice, game)) {
                switch (choice.getChoice()) {
                    case "Banding":
                        gainedAbility = BandingAbility.getInstance();
                        break;
                    case "First strike":
                        gainedAbility = FirstStrikeAbility.getInstance();
                        break;
                    case "Trample":
                        gainedAbility = TrampleAbility.getInstance();
                }
            }
            if (gainedAbility != null) {
                game.addEffect(new GainAbilityTargetEffect(gainedAbility, Duration.Custom), source);
            } else {
                targetPermanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                game.informPlayers(controller.getLogName() + " puts a +1/+1 counter on " + targetPermanent.getLogName());
            }
            return true;
        }
        return false;
    }
}
