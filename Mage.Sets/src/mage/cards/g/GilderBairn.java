
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class GilderBairn extends CardImpl {

    public GilderBairn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/U}{G/U}");
        this.subtype.add(SubType.OUPHE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{GU}, {untap}: For each counter on target permanent, put another of those counters on that permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GilderBairnEffect(), new ManaCostsImpl<>("{2}{G/U}"));
        ability.addCost(new UntapSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

    }

    private GilderBairn(final GilderBairn card) {
        super(card);
    }

    @Override
    public GilderBairn copy() {
        return new GilderBairn(this);
    }
}

class GilderBairnEffect extends OneShotEffect {

    public GilderBairnEffect() {
        super(Outcome.Benefit);
        this.staticText = "Double the number of each kind of counter on target permanent";
    }

    public GilderBairnEffect(final GilderBairnEffect effect) {
        super(effect);
    }

    @Override
    public GilderBairnEffect copy() {
        return new GilderBairnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            for (Counter counter : target.getCounters(game).values()) {
                Counter newCounter = new Counter(counter.getName(), counter.getCount());
                target.addCounters(newCounter, source.getControllerId(), source, game);
            }
        }
        return false;
    }
}
