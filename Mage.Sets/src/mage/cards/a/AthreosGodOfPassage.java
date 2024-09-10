package mage.cards.a;

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
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AthreosGodOfPassage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you own");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public AthreosGodOfPassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to white and black is less than seven, Athreos isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.WB, 7))
                .addHint(DevotionCount.WB.getHint()));

        // Whenever another creature you own dies, return it to your hand unless target opponent pays 3 life.
        Ability ability = new AthreosDiesCreatureTriggeredAbility(new AthreosGodOfPassageReturnEffect(), false, filter);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    private AthreosGodOfPassage(final AthreosGodOfPassage card) {
        super(card);
    }

    @Override
    public AthreosGodOfPassage copy() {
        return new AthreosGodOfPassage(this);
    }
}

class AthreosGodOfPassageReturnEffect extends OneShotEffect {

    AthreosGodOfPassageReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return it to your hand unless target opponent pays 3 life";
    }

    private AthreosGodOfPassageReturnEffect(final AthreosGodOfPassageReturnEffect effect) {
        super(effect);
    }

    @Override
    public AthreosGodOfPassageReturnEffect copy() {
        return new AthreosGodOfPassageReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        UUID creatureId = (UUID) this.getValue("creatureId");
        Card creature = game.getCard(creatureId);
        if (creature == null) {
            return true;
        }
        Player opponent = game.getPlayer(source.getFirstTarget());
        boolean paid = false;
        if (opponent != null) {
            Cost cost = new PayLifeCost(3);
            if (cost.canPay(source, source, opponent.getId(), game)
                    && opponent.chooseUse(outcome, "Pay 3 life to prevent that " + creature.getLogName() + " returns to " + controller.getLogName() + "'s hand?", source, game)
                    && cost.pay(source, game, source, opponent.getId(), false, null)) {
                paid = true;
            }
        }
        if ((opponent != null && paid) || game.getState().getZone(creature.getId()) != Zone.GRAVEYARD) {
            return true;
        }
        controller.moveCards(creature, Zone.HAND, source, game);
        return true;
    }
}

class AthreosDiesCreatureTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterCreaturePermanent filter;

    AthreosDiesCreatureTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        setTriggerPhrase("Whenever " + filter.getMessage() + " dies, ");
    }

    private AthreosDiesCreatureTriggeredAbility(AthreosDiesCreatureTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public AthreosDiesCreatureTriggeredAbility copy() {
        return new AthreosDiesCreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()) {
            return false;
        }
        if (zEvent.getTarget() == null || !filter.match(zEvent.getTarget(), controllerId, this, game)) {
            return false;
        }
        for (Effect effect : this.getEffects()) {
            effect.setValue("creatureId", event.getTargetId());
        }
        return true;
    }
}
