package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NeverwinterHydra extends CardImpl {

    public NeverwinterHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{X}{G}{G}");

        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Neverwinter Hydra enters the battlefield, roll X d6. It enters with a number of +1/+1 counters on it equal to the total of those results.
        this.addAbility(new AsEntersBattlefieldAbility(new NeverwinterHydraEffect()));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward {4}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{4}")));
    }

    private NeverwinterHydra(final NeverwinterHydra card) {
        super(card);
    }

    @Override
    public NeverwinterHydra copy() {
        return new NeverwinterHydra(this);
    }
}

class NeverwinterHydraEffect extends OneShotEffect {

    NeverwinterHydraEffect() {
        super(Outcome.Benefit);
        staticText = "roll X d6. It enters with a number of +1/+1 counters on it equal to the total of those results";
    }

    private NeverwinterHydraEffect(final NeverwinterHydraEffect effect) {
        super(effect);
    }

    @Override
    public NeverwinterHydraEffect copy() {
        return new NeverwinterHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return true;
        }
        SpellAbility spellAbility = (SpellAbility) getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
        if (spellAbility == null
                || !spellAbility.getSourceId().equals(source.getSourceId())
                || permanent.getZoneChangeCounter(game) != spellAbility.getSourceObjectZoneChangeCounter()) {
            return true;
        }
        if (!spellAbility.getSourceId().equals(source.getSourceId())) {
            return true;
        } // put into play by normal cast
        int xValue = spellAbility.getManaCostsToPay().getX();
        if (xValue < 1) {
            return false;
        }
        int amount = player.rollDice(outcome, source, game, 6, xValue, 0).stream().mapToInt(x -> x).sum();
        List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");
        permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game, appliedEffects);
        return true;
    }
}
