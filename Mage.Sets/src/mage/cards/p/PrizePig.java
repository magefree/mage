package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrizePig extends CardImpl {

    public PrizePig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Whenever you gain life, put that many ribbon counters on Prize Pig. Then if there are three or more ribbon counters on Prize Pig, remove those counters and untap it.
        this.addAbility(new GainLifeControllerTriggeredAbility(new PrizePigEffect()));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private PrizePig(final PrizePig card) {
        super(card);
    }

    @Override
    public PrizePig copy() {
        return new PrizePig(this);
    }
}

class PrizePigEffect extends OneShotEffect {

    PrizePigEffect() {
        super(Outcome.Benefit);
        staticText = "put that many ribbon counters on {this}. Then if there are three " +
                "or more ribbon counters on {this}, remove those counters and untap it";
    }

    private PrizePigEffect(final PrizePigEffect effect) {
        super(effect);
    }

    @Override
    public PrizePigEffect copy() {
        return new PrizePigEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        int amount = (Integer) getValue("gainedLife");
        if (permanent == null || amount < 1) {
            return false;
        }
        permanent.addCounters(CounterType.RIBBON.createInstance(amount), source, game);
        int count = permanent.getCounters(game).getCount(CounterType.RIBBON);
        if (count >= 3) {
            permanent.removeCounters(CounterType.RIBBON.createInstance(count), source, game);
            permanent.untap(game);
        }
        return true;
    }
}
