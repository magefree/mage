
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ZameckGuildmage extends CardImpl {

    public ZameckGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");
        this.subtype.add(SubType.ELF, SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}{U}: This turn, each creature you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ZameckGuildmageEntersBattlefieldEffect(), new ManaCostsImpl<>("{G}{U}")));

        // {G}{U}, Remove a +1/+1 counter from a creature you control: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{G}{U}"));
        ability.addCost(new RemoveCounterCost(new TargetControlledCreaturePermanent(), CounterType.P1P1));
        this.addAbility(ability);
    }

    private ZameckGuildmage(final ZameckGuildmage card) {
        super(card);
    }

    @Override
    public ZameckGuildmage copy() {
        return new ZameckGuildmage(this);
    }
}

class ZameckGuildmageEntersBattlefieldEffect extends ReplacementEffectImpl {

    public ZameckGuildmageEntersBattlefieldEffect() {
        super(Duration.EndOfTurn, Outcome.BoostCreature);
        this.staticText = "This turn, each creature you control enters the battlefield with an additional +1/+1 counter on it";
    }

    public ZameckGuildmageEntersBattlefieldEffect(ZameckGuildmageEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null && permanent.isControlledBy(source.getControllerId()) && permanent.isCreature(game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public ZameckGuildmageEntersBattlefieldEffect copy() {
        return new ZameckGuildmageEntersBattlefieldEffect(this);
    }
}
