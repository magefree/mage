package mage.cards.d;

import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.command.emblems.DomriChaosBringerEmblem;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;
import mage.game.stack.StackObject;

/**
 * @author TheElk801
 */
public final class DomriChaosBringer extends CardImpl {

    public DomriChaosBringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOMRI);
        this.setStartingLoyalty(5);

        // +1: Add {R} or {G}. If that mana is spent on a creature spell, it gains riot.
        this.addAbility(new LoyaltyAbility(new DomriChaosBringerEffect(), 1));

        // −3: Look at the top four cards of your library. You may reveal up to two 
        // creature cards from among them and put them into your hand. Put the rest 
        // on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(
                4, 2, StaticFilters.FILTER_CARD_CREATURES, PutCards.HAND, PutCards.BOTTOM_RANDOM), -3));

        // −8: You get an emblem with "At the beginning of each end step, create a 4/4 red 
        // and green Beast creature token with trample."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new DomriChaosBringerEmblem()), -8));
    }

    private DomriChaosBringer(final DomriChaosBringer card) {
        super(card);
    }

    @Override
    public DomriChaosBringer copy() {
        return new DomriChaosBringer(this);
    }
}

class DomriChaosBringerEffect extends OneShotEffect {

    DomriChaosBringerEffect() {
        super(Outcome.Benefit);
        staticText = "Add {R} or {G}. If that mana is spent on a creature spell, it gains riot.";
    }

    private DomriChaosBringerEffect(final DomriChaosBringerEffect effect) {
        super(effect);
    }

    @Override
    public DomriChaosBringerEffect copy() {
        return new DomriChaosBringerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ManaEffect manaEffect;
        if (player.chooseUse(Outcome.PutManaInPool, "Choose red or green mana", "", "Red", "Green", source, game)) {
            manaEffect = new BasicManaEffect(Mana.RedMana(1));
        } else {
            manaEffect = new BasicManaEffect(Mana.GreenMana(1));
        }
        game.addDelayedTriggeredAbility(new DomriChaosBringerTriggeredAbility(source.getSourceId(), game.getTurnNum()), source);
        return manaEffect.apply(game, source);
    }
}

class DomriChaosBringerTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID spellId;
    private final int turnNumber;

    DomriChaosBringerTriggeredAbility(UUID spellId, int turnNumber) {
        super(null, Duration.Custom, true);
        this.spellId = spellId;
        this.turnNumber = turnNumber;
        this.usesStack = false;
    }

    private DomriChaosBringerTriggeredAbility(final DomriChaosBringerTriggeredAbility ability) {
        super(ability);
        this.spellId = ability.spellId;
        this.turnNumber = ability.turnNumber;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MANA_PAID;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(spellId)) {
            return false;
        }
        if (game.getTurnNum() != turnNumber) {
            return false;
        }
        MageObject mo = game.getObject(event.getTargetId());
        if (mo == null || !mo.isCreature(game)) {
            return false;
        }
        
        StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
        
        if (stackObject == null) {
            return false;
        }
        this.getEffects().clear();
        FilterCard filter = new FilterCard();
        filter.add(new CardIdPredicate(stackObject.getSourceId()));
        this.addEffect(new GainAbilityControlledSpellsEffect(new RiotAbility(), filter));
        return true;
    }

    @Override
    public DomriChaosBringerTriggeredAbility copy() {
        return new DomriChaosBringerTriggeredAbility(this);
    }
}
