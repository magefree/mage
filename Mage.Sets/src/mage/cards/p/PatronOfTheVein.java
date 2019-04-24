
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class PatronOfTheVein extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public PatronOfTheVein(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Patron of the Vein enters the battlefield, destroy target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // Whenever a creature an opponent controls dies, exile it and put a +1/+1 counter on each Vampire you control.
        Ability ability2 = new PatronOfTheVeinCreatureDiesTriggeredAbility();
        this.addAbility(ability2);
    }

    public PatronOfTheVein(final PatronOfTheVein card) {
        super(card);
    }

    @Override
    public PatronOfTheVein copy() {
        return new PatronOfTheVein(this);
    }
}

class PatronOfTheVeinCreatureDiesTriggeredAbility extends TriggeredAbilityImpl {

    public PatronOfTheVeinCreatureDiesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PatronOfTheVeinExileCreatureEffect(), false);
    }

    public PatronOfTheVeinCreatureDiesTriggeredAbility(final PatronOfTheVeinCreatureDiesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PatronOfTheVeinCreatureDiesTriggeredAbility copy() {
        return new PatronOfTheVeinCreatureDiesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).isDiesEvent()) {
            if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
                Card creature = game.getPermanentOrLKIBattlefield(event.getTargetId());
                if (creature != null && creature.isCreature()) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(creature.getId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls dies, exile it and put a +1/+1 counter on each Vampire you control";
    }
}

class PatronOfTheVeinExileCreatureEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new SubtypePredicate(SubType.VAMPIRE));
    }

    public PatronOfTheVeinExileCreatureEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile it and put a +1/+1 counter on each Vampire you control";
    }

    public PatronOfTheVeinExileCreatureEffect(final PatronOfTheVeinExileCreatureEffect effect) {
        super(effect);
    }

    @Override
    public PatronOfTheVeinExileCreatureEffect copy() {
        return new PatronOfTheVeinExileCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));

        if (card != null) {
            Effect effect = new ExileTargetEffect();
            effect.setTargetPointer(new FixedTarget(card.getId()));
            effect.apply(game, source);
        }

        for (Permanent permanent : game.getState().getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
            game.informPlayers(sourceObject.getName() + ": Put a +1/+1 counter on " + permanent.getLogName());
        }
        return true;

    }
}
