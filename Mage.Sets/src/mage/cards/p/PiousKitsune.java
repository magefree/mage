
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class PiousKitsune extends CardImpl {

    public PiousKitsune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, put a devotion counter on Pious Kitsune. Then if a creature named Eight-and-a-Half-Tails is on the battlefield, you gain 1 life for each devotion counter on Pious Kitsune.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new PiousKitsuneEffect(), TargetController.YOU, false));
        // {tap}, Remove a devotion counter from Pious Kitsune: You gain 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.DEVOTION.createInstance()));
        this.addAbility(ability);
        
    }

    private PiousKitsune(final PiousKitsune card) {
        super(card);
    }

    @Override
    public PiousKitsune copy() {
        return new PiousKitsune(this);
    }
}

class PiousKitsuneEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature named Eight-and-a-Half-Tails");
    static {
        filter.add(new NamePredicate("Eight-and-a-Half-Tails"));
    }

    public PiousKitsuneEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a devotion counter on Pious Kitsune. Then if a creature named Eight-and-a-Half-Tails is on the battlefield, you gain 1 life for each devotion counter on Pious Kitsune";
    }

    public PiousKitsuneEffect(final PiousKitsuneEffect effect) {
        super(effect);
    }

    @Override
    public PiousKitsuneEffect copy() {
        return new PiousKitsuneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result;
        result = new AddCountersSourceEffect(CounterType.DEVOTION.createInstance()).apply(game, source);
        if (game.getBattlefield().count(filter, source.getControllerId(), source, game) > 0) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                int life = permanent.getCounters(game).getCount(CounterType.DEVOTION);
                if (life > 0) {
                    Player controller = game.getPlayer(source.getControllerId());
                    if (controller != null) {
                        controller.gainLife(life, game, source);
                    }
                }
            }
        }
        return result;
    }
}
