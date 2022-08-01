package mage.cards.n;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.emblems.NarsetTranscendentEmblem;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class NarsetTranscendent extends CardImpl {

    public NarsetTranscendent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NARSET);

        this.setStartingLoyalty(6);

        // +1: Look at the top card of your library. If it's a noncreature, nonland card, you may reveal it and put it into your hand.
        this.addAbility(new LoyaltyAbility(new NarsetTranscendentEffect1(), 1));

        // -2: When you cast your next instant or sorcery spell from your hand this turn, it gains rebound.
        this.addAbility(new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(new NarsetTranscendentTriggeredAbility()), -2));

        // -9:You get an emblem with "Your opponents can't cast noncreature spells."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new NarsetTranscendentEmblem()), -9));
    }

    private NarsetTranscendent(final NarsetTranscendent card) {
        super(card);
    }

    @Override
    public NarsetTranscendent copy() {
        return new NarsetTranscendent(this);
    }
}

class NarsetTranscendentEffect1 extends OneShotEffect {

    public NarsetTranscendentEffect1() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top card of your library. If it's a noncreature, nonland card, you may reveal it and put it into your hand";
    }

    public NarsetTranscendentEffect1(final NarsetTranscendentEffect1 effect) {
        super(effect);
    }

    @Override
    public NarsetTranscendentEffect1 copy() {
        return new NarsetTranscendentEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                CardsImpl cards = new CardsImpl();
                cards.add(card);
                controller.lookAtCards(sourceObject.getIdName(), cards, game);
                if (!card.isCreature(game) && !card.isLand(game)) {
                    if (controller.chooseUse(outcome, "Reveal " + card.getLogName() + " and put it into your hand?", source, game)) {
                        controller.moveCards(card, Zone.HAND, source, game);
                        controller.revealCards(sourceObject.getIdName(), cards, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class NarsetTranscendentTriggeredAbility extends DelayedTriggeredAbility {

    public NarsetTranscendentTriggeredAbility() {
        super(new NarsetTranscendentGainReboundEffect(), Duration.EndOfTurn, true);
    }

    private NarsetTranscendentTriggeredAbility(final NarsetTranscendentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NarsetTranscendentTriggeredAbility copy() {
        return new NarsetTranscendentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.getFromZone() == Zone.HAND) {
                if (spell.getCard() != null
                        && spell.getCard().isInstantOrSorcery(game)) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(spell.getId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "When you cast your next instant or sorcery spell from your hand this turn, " ;
    }
}

class NarsetTranscendentGainReboundEffect extends ContinuousEffectImpl {

    public NarsetTranscendentGainReboundEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "it gains rebound";
    }

    public NarsetTranscendentGainReboundEffect(final NarsetTranscendentGainReboundEffect effect) {
        super(effect);
    }

    @Override
    public NarsetTranscendentGainReboundEffect copy() {
        return new NarsetTranscendentGainReboundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
            if (spell != null) {
                Card card = spell.getCard();
                if (card != null) {
                    addReboundAbility(card, game);
                }
            } else {
                discard();
            }
            return true;
        }
        return false;
    }

    private void addReboundAbility(Card card, Game game) {
        boolean found = false;
        for (Ability ability : card.getAbilities(game)) {
            if (ability instanceof ReboundAbility) {
                found = true;
                break;
            }
        }
        if (!found) {
            Ability ability = new ReboundAbility();
            game.getState().addOtherAbility(card, ability);
        }
    }
}
