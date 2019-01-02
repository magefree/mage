
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class AthreosGodOfPassage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you own");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public AthreosGodOfPassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to white and black is less than seven, Athreos isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.W, ColoredManaSymbol.B), 7);
        effect.setText("As long as your devotion to white and black is less than seven, Athreos isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // Whenever another creature you own dies, return it to your hand unless target opponent pays 3 life.
        Ability ability = new AthreosDiesCreatureTriggeredAbility(new AthreosGodOfPassageReturnEffect(), false, filter);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    public AthreosGodOfPassage(final AthreosGodOfPassage card) {
        super(card);
    }

    @Override
    public AthreosGodOfPassage copy() {
        return new AthreosGodOfPassage(this);
    }
}

class AthreosGodOfPassageReturnEffect extends OneShotEffect {

    public AthreosGodOfPassageReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return it to your hand unless target opponent pays 3 life";
    }

    public AthreosGodOfPassageReturnEffect(final AthreosGodOfPassageReturnEffect effect) {
        super(effect);
    }

    @Override
    public AthreosGodOfPassageReturnEffect copy() {
        return new AthreosGodOfPassageReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID creatureId = (UUID) this.getValue("creatureId");
            Card creature = game.getCard(creatureId);
            if (creature != null) {
                Player opponent = game.getPlayer(source.getFirstTarget());
                boolean paid = false;
                if (opponent != null) {
                    Cost cost = new PayLifeCost(3);
                    if (cost.canPay(source, source.getSourceId(), opponent.getId(), game)
                            && opponent.chooseUse(outcome, "Pay 3 life to prevent that " + creature.getLogName() + " returns to " + controller.getLogName() + "'s hand?", source, game)) {
                        if (cost.pay(source, game, source.getSourceId(), opponent.getId(), false, null)) {
                            paid = true;
                        }
                    }
                }
                if (opponent == null || !paid) {
                    if (game.getState().getZone(creature.getId()) == Zone.GRAVEYARD) {
                        controller.moveCards(creature, Zone.HAND, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class AthreosDiesCreatureTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterCreaturePermanent filter;

    public AthreosDiesCreatureTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
    }

    public AthreosDiesCreatureTriggeredAbility(AthreosDiesCreatureTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public AthreosDiesCreatureTriggeredAbility copy() {
        return new AthreosDiesCreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
            if (zEvent.getTarget() != null && filter.match(zEvent.getTarget(), sourceId, controllerId, game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("creatureId", event.getTargetId());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever " + filter.getMessage() + " dies, " + super.getRule();
    }
}
