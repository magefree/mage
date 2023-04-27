package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.ManaUtil;

import java.util.Objects;
import java.util.UUID;


/**
 * @author TGower
 */
public final class MysticRemora extends CardImpl {

    public MysticRemora(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));
        // Whenever an opponent casts a noncreature spell, you may draw a card unless that player pays {4}.
        this.addAbility(new MysticRemoraTriggeredAbility());

    }

    private MysticRemora(final MysticRemora card) {
        super(card);
    }

    @Override
    public MysticRemora copy() {
        return new MysticRemora(this);
    }
}

class MysticRemoraTriggeredAbility extends TriggeredAbilityImpl {


    public MysticRemoraTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MysticRemoraEffect(), false);

    }

    public MysticRemoraTriggeredAbility(final MysticRemoraTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MysticRemoraTriggeredAbility copy() {
        return new MysticRemoraTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            if (event.getType() == GameEvent.EventType.SPELL_CAST) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && !spell.isCreature(game)) {
                    Player controller = game.getPlayer(game.getControllerId(this.controllerId));
                    Player player = game.getPlayer(spell.getControllerId());
                    if (!Objects.equals(controller, player)) {
                        this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a noncreature spell, you may draw a card unless that player pays {4}.";
    }
}

class MysticRemoraEffect extends OneShotEffect {

    public MysticRemoraEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you may draw a card unless that player pays {4}";
    }

    public MysticRemoraEffect(final MysticRemoraEffect effect) {
        super(effect);
    }

    @Override
    public MysticRemoraEffect copy() {
        return new MysticRemoraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && opponent != null && sourceObject != null) {
            if (controller.chooseUse(Outcome.DrawCard, "Draw a card (" + sourceObject.getLogName() + ')', source, game)) {
                Cost cost = ManaUtil.createManaCost(4, false);
                if (opponent.chooseUse(Outcome.Benefit, "Pay {4}?", source, game)
                        && cost.pay(source, game, source, opponent.getId(), false, null)) {
                    return true;
                }
                controller.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }

}

    

    
