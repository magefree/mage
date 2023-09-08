package mage.cards.h;

import java.util.UUID;
import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author LevelX2
 */
public final class HarnessTheStorm extends CardImpl {

    public HarnessTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever you cast an instant or sorcery spell from your hand, you may cast 
        // target card with the same name as that spell from your graveyard.
        this.addAbility(new HarnessTheStormTriggeredAbility(new HarnessTheStormEffect(),
                new FilterInstantOrSorcerySpell("an instant or sorcery spell from your hand"),
                false), new CastFromHandWatcher());
    }

    private HarnessTheStorm(final HarnessTheStorm card) {
        super(card);
    }

    @Override
    public HarnessTheStorm copy() {
        return new HarnessTheStorm(this);
    }

}

class HarnessTheStormTriggeredAbility extends SpellCastControllerTriggeredAbility {

    public HarnessTheStormTriggeredAbility(Effect effect, FilterSpell filter, boolean optional) {
        super(effect, filter, optional);
    }

    private HarnessTheStormTriggeredAbility(final HarnessTheStormTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            CastFromHandWatcher watcher = game.getState().getWatcher(CastFromHandWatcher.class);
            if (watcher != null && watcher.spellWasCastFromHand(event.getSourceId())) {
                Spell spell = game.getState().getStack().getSpell(event.getSourceId());
                if (spell != null) {
                    FilterCard filterCard = new FilterCard("a card named " + spell.getName() + " in your graveyard");
                    filterCard.add(new NamePredicate(spell.getName()));
                    this.getTargets().clear();
                    this.getTargets().add(new TargetCardInYourGraveyard(filterCard));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public HarnessTheStormTriggeredAbility copy() {
        return new HarnessTheStormTriggeredAbility(this);
    }

}

class HarnessTheStormEffect extends OneShotEffect {

    public HarnessTheStormEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may cast target card with the same name as that "
                + "spell from your graveyard. <i>(you still pay its costs.)</i>";
    }

    private HarnessTheStormEffect(final HarnessTheStormEffect effect) {
        super(effect);
    }

    @Override
    public HarnessTheStormEffect copy() {
        return new HarnessTheStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            Card card = controller.getGraveyard().get(getTargetPointer().getFirst(game, source), game);
            if (card != null) {
                if (controller.chooseUse(outcome.Benefit, "Cast " + card.getIdName() + " from your graveyard?", source, game)) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                    controller.cast(controller.chooseAbilityForCast(card, game, false),
                            game, false, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                }
            }
            return true;
        }

        return false;
    }
}
