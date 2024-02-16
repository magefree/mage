
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class Hydradoodle extends CardImpl {

    public Hydradoodle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{X}{G}{G}");

        this.subtype.add(SubType.HYDRA);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Hydradoodle enters the battlefield, roll X six-sided dice. Hydradoodle enters the battlefield with a number of +1/+1 counters on it equal to the total of those results.
        this.addAbility(new EntersBattlefieldAbility(new HydradoodleEffect(),
                null,
                "As {this} enters the battlefield, roll X six-sided dice. {this} enters the battlefield with a number of +1/+1 counters on it equal to the total of those results",
                null));
        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private Hydradoodle(final Hydradoodle card) {
        super(card);
    }

    @Override
    public Hydradoodle copy() {
        return new Hydradoodle(this);
    }
}

class HydradoodleEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("permanent with a counter");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    HydradoodleEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "roll X six-sided dice. {this} enters the battlefield with a number of +1/+1 counters on it equal to the total of those results";
    }

    private HydradoodleEffect(final HydradoodleEffect effect) {
        super(effect);
    }

    @Override
    public HydradoodleEffect copy() {
        return new HydradoodleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent != null && controller != null) {
            int amount = CardUtil.getSourceCostsTag(game, source, "X", 0);
            if (amount > 0) {
                int total = controller.rollDice(outcome, source, game, 6, amount, 0).stream().mapToInt(x -> x).sum();
                List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");
                permanent.addCounters(CounterType.P1P1.createInstance(total), source.getControllerId(), source, game, appliedEffects);
            }
            return true;
        }
        return false;
    }
}
