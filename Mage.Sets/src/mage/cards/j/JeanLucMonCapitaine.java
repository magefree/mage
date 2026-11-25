package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.FederationCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Grath
 */
public final class JeanLucMonCapitaine extends CardImpl {

    public JeanLucMonCapitaine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Federation -- Faeries you control enter with an additional +1/+1 counter on them for each creature type
        // among non-Borg creatures you already control.
        this.addAbility(new SimpleStaticAbility(new JeanLucMonCapitaineEffect())
                .setAbilityWord(AbilityWord.FEDERATION).addHint(FederationCount.instance.getHint()));
    }

    private JeanLucMonCapitaine(final JeanLucMonCapitaine card) {
        super(card);
    }

    @Override
    public JeanLucMonCapitaine copy() {
        return new JeanLucMonCapitaine(this);
    }
}

class JeanLucMonCapitaineEffect extends ReplacementEffectImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.FAERIE);

    JeanLucMonCapitaineEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Faeries you control enter with an additional +1/+1 counter on them for each creature " +
                "type among non-Borg creatures you already control.";
    }

    private JeanLucMonCapitaineEffect(JeanLucMonCapitaineEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null && creature.isControlledBy(source.getControllerId())
                && creature.isCreature(game)
                && filter.match(creature, source.getControllerId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (creature == null || controller == null) {
            return false;
        }
        int num = FederationCount.instance.calculate(game, source, this);
        if (num > 0) {
            creature.addCounters(CounterType.P1P1.createInstance(num), source.getControllerId(), source, game);
        }
        return false;
    }

    @Override
    public JeanLucMonCapitaineEffect copy() {
        return new JeanLucMonCapitaineEffect(this);
    }
}