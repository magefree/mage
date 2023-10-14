package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author Susucr
 */
public final class ExtraordinaryJourney extends CardImpl {

    public ExtraordinaryJourney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{X}{U}{U}");
        

        // When Extraordinary Journey enters the battlefield, exile up to X target creatures. For each of those cards, its owner may play it for as long as it remains exiled.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExtraordinaryJourneyEffect());
        ability.setTargetAdjuster(ExtraordinaryJourneyAdjuster.instance);
        this.addAbility(ability);

        // Whenever one or more nontoken creatures enter the battlefield, if one or more of them entered from exile or was cast from exile, you draw a card. This ability triggers only once each turn.
        this.addAbility(new ExtraordinaryJourneyTriggeredAbility());
    }

    private ExtraordinaryJourney(final ExtraordinaryJourney card) {
        super(card);
    }

    @Override
    public ExtraordinaryJourney copy() {
        return new ExtraordinaryJourney(this);
    }
}

enum ExtraordinaryJourneyAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(0, ManacostVariableValue.ETB.calculate(game, ability, null)));
    }
}

class ExtraordinaryJourneyEffect extends OneShotEffect {

    ExtraordinaryJourneyEffect() {
        super(Outcome.Neutral);
        this.staticText = "exile up to X target creatures. For each of those cards, "
                + "its owner may play it for as long as it remains exiled";
    }

    private ExtraordinaryJourneyEffect(final ExtraordinaryJourneyEffect effect) {
        super(effect);
    }

    @Override
    public ExtraordinaryJourneyEffect copy() {
        return new ExtraordinaryJourneyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = source
                .getTargets().get(0).getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // exile up to X target creatures.
        Effect effect = new ExileTargetEffect().setTargetPointer(new FixedTargets(permanents, game));
        if(!effect.apply(game, source)) {
            return false;
        }

        Set<Card> cards = permanents
                .stream()
                .map(Card::getMainCard)
                .filter(Objects::nonNull)
                .filter(card -> game.getState().getZone(card.getId()) == Zone.EXILED)
                .collect(Collectors.toSet());

        Set<Player> owners = cards
                .stream()
                .map(Card::getOwnerId)
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // For each of those cards, its owner may play it for as long as it remains exiled.
        for (Player owner : owners) {
            String exileZoneName = "Exile — Can be played by " + owner.getName();
            UUID exileZoneId = CardUtil.getExileZoneId(
                    exileZoneName,
                    game
            );

            ExileZone zone = game.getState().getExile().createZone(exileZoneId, exileZoneName);

            for(Card card : cards) {
                if (card.getOwnerId().equals(owner.getId())) {
                    game.getExile().moveToAnotherZone(card, game, zone);
                    CardUtil.makeCardPlayable(
                            game, source, card, Duration.Custom,
                            false, card.getOwnerId(), null
                    );
                }
            }
        }

        return true;
    }
}

class ExtraordinaryJourneyTriggeredAbility extends TriggeredAbilityImpl {

    ExtraordinaryJourneyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1, "you"), false);
        setTriggerPhrase("Whenever one or more nontoken creatures enter the battlefield, "
            + "if one or more of them entered from exile or was cast from exile, ");
        setTriggersOnceEachTurn(true);
    }

    private ExtraordinaryJourneyTriggeredAbility(final ExtraordinaryJourneyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ExtraordinaryJourneyTriggeredAbility copy() {
        return new ExtraordinaryJourneyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        EntersTheBattlefieldEvent zEvent = (EntersTheBattlefieldEvent) event;
        if(zEvent == null){
            return false;
        }

        Permanent permanent = zEvent.getTarget();
        if(permanent == null || !permanent.isCreature(game)) {
            return false;
        }

        Zone fromZone = zEvent.getFromZone();
        if(fromZone == Zone.EXILED) {
            // Directly from exile
            return true;
        }

        if(fromZone == Zone.STACK) {
            // Get spell in the stack.
            Spell spell = game.getSpellOrLKIStack(permanent.getId());
            if(spell != null && spell.getFromZone() == Zone.EXILED) {
                // Creature was cast from exile
                return true;
            }
        }

        return false;
    }
}
