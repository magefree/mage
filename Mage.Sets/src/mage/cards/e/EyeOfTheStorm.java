package mage.cards.e;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class EyeOfTheStorm extends CardImpl {

    public EyeOfTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}{U}");

        // Whenever a player casts an instant or sorcery card, exile it. Then that player copies each instant or sorcery card exiled with Eye of the Storm. For each copy, the player may cast the copy without paying its mana cost.
        this.addAbility(new EyeOfTheStormAbility());
    }

    private EyeOfTheStorm(final EyeOfTheStorm card) {
        super(card);
    }

    @Override
    public EyeOfTheStorm copy() {
        return new EyeOfTheStorm(this);
    }
}

class EyeOfTheStormAbility extends TriggeredAbilityImpl {

    public EyeOfTheStormAbility() {
        super(Zone.BATTLEFIELD, new EyeOfTheStormEffect1(), false);
    }

    public EyeOfTheStormAbility(final EyeOfTheStormAbility ability) {
        super(ability);
    }

    @Override
    public EyeOfTheStormAbility copy() {
        return new EyeOfTheStormAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null
                && !spell.isCopy()
                && spell.getCard() != null
                && !spell.getCard().isCopy()
                && spell.isInstantOrSorcery(game)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

}

class EyeOfTheStormEffect1 extends OneShotEffect {

    public EyeOfTheStormEffect1() {
        super(Outcome.Neutral);
        staticText = "Whenever a player casts an instant or sorcery card, exile it. "
                + "Then that player copies each instant or sorcery card exiled with {this}. "
                + "For each copy, the player may cast the copy without paying its mana cost";
    }

    public EyeOfTheStormEffect1(final EyeOfTheStormEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        boolean noLongerOnStack = false;// spell was exiled already by another effect, for example NivMagus Elemental
        if (spell == null) {
            spell = ((Spell) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.STACK));
            noLongerOnStack = true;
        }
        Permanent eyeOfTheStorm = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (spell != null && eyeOfTheStorm != null) {
            Player spellController = game.getPlayer(spell.getControllerId());
            Card card = spell.getCard();
            if (spellController == null
                    || card == null
                    || !StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY.match(spell, game)) {
                return false;
            }
            if (!noLongerOnStack) {// the spell is still on the stack, so exile it
                UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), eyeOfTheStorm.getZoneChangeCounter(game));
                spellController.moveCardsToExile(spell, source, game, true, exileZoneId, eyeOfTheStorm.getIdName());
            }

            eyeOfTheStorm.imprint(card.getId(), game);// technically, using the imprint functionality here is not correct.

            if (eyeOfTheStorm.getImprinted() != null
                    && !eyeOfTheStorm.getImprinted().isEmpty()) {
                CardsImpl copiedCards = new CardsImpl();
                for (UUID uuid : eyeOfTheStorm.getImprinted()) {
                    // Check if owner of card is still in game
                    card = game.getCard(uuid);
                    if (card != null && game.getPlayer(card.getOwnerId()) != null) {
                        if (card instanceof SplitCard) {
                            copiedCards.add(((SplitCard) card).getLeftHalfCard());
                            copiedCards.add(((SplitCard) card).getRightHalfCard());
                        } else if (card instanceof ModalDoubleFacesCard) {
                            copiedCards.add(((ModalDoubleFacesCard) card).getLeftHalfCard());
                            copiedCards.add(((ModalDoubleFacesCard) card).getRightHalfCard());
                        } else {
                            copiedCards.add(card);
                        }
                    }
                }

                boolean continueCasting = true;
                while (spellController.canRespond() && continueCasting) {
                    continueCasting = copiedCards.size() > 1 && spellController.chooseUse(outcome, "Cast one of the copied cards without paying its mana cost?", source, game);

                    Card cardToCopy;
                    if (copiedCards.size() == 1) {
                        cardToCopy = copiedCards.getCards(game).iterator().next();
                    } else {
                        TargetCard target = new TargetCard(1, Zone.EXILED, new FilterCard("card to copy"));
                        spellController.choose(Outcome.Copy, copiedCards, target, game);
                        cardToCopy = copiedCards.get(target.getFirstTarget(), game);
                        copiedCards.remove(cardToCopy);
                    }
                    if (cardToCopy != null) {
                        Card copy = game.copyCard(cardToCopy, source, source.getControllerId());
                        if (spellController.chooseUse(outcome, "Cast " + copy.getIdName() + " without paying mana cost?", source, game)) {
                            game.getState().setValue("PlayFromNotOwnHandZone" + copy.getId(), Boolean.TRUE);
                            spellController.cast(spellController.chooseAbilityForCast(copy, game, true), game, true, new ApprovingObject(source, game));
                            game.getState().setValue("PlayFromNotOwnHandZone" + copy.getId(), null);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public EyeOfTheStormEffect1 copy() {
        return new EyeOfTheStormEffect1(this);
    }
}
