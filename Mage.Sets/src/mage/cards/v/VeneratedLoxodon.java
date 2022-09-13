package mage.cards.v;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.EachCreatureThatConvokedSourceWatcher;

/**
 *
 * @author LevelX2
 */
public final class VeneratedLoxodon extends CardImpl {

    public VeneratedLoxodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // When Venerated Loxodon enters the battlefield, put a +1/+1 counter on each creature that convoked it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VeneratedLoxodonEffect(), false), new EachCreatureThatConvokedSourceWatcher());
    }

    private VeneratedLoxodon(final VeneratedLoxodon card) {
        super(card);
    }

    @Override
    public VeneratedLoxodon copy() {
        return new VeneratedLoxodon(this);
    }
}

class VeneratedLoxodonEffect extends OneShotEffect {

    public VeneratedLoxodonEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "put a +1/+1 counter on each creature that convoked it";
    }

    public VeneratedLoxodonEffect(final VeneratedLoxodonEffect effect) {
        super(effect);
    }

    @Override
    public VeneratedLoxodonEffect copy() {
        return new VeneratedLoxodonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        EachCreatureThatConvokedSourceWatcher watcher = game.getState().getWatcher(EachCreatureThatConvokedSourceWatcher.class);
        if (watcher != null) {
            MageObjectReference mor = new MageObjectReference(source.getSourceId(), source.getSourceObjectZoneChangeCounter() - 1, game); // -1 because of spell on the stack
            Set<MageObjectReference> creatures = watcher.getConvokingCreatures(mor);
            if (creatures != null) {
                for (MageObjectReference creatureMOR : creatures) {
                    Permanent creature = creatureMOR.getPermanent(game);
                    if (creature != null) {
                        creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

