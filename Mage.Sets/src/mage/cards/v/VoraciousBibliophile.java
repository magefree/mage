package mage.cards.v;

import java.util.Collection;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author Jmlundeen
 */
public final class VoraciousBibliophile extends CardImpl {

    public VoraciousBibliophile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you cast a spell with one or more targets, draw that many cards.
        this.addAbility(new VoraciousBibliophileTriggeredAbility());
    }

    private VoraciousBibliophile(final VoraciousBibliophile card) {
        super(card);
    }

    @Override
    public VoraciousBibliophile copy() {
        return new VoraciousBibliophile(this);
    }
}

class VoraciousBibliophileTriggeredAbility extends TriggeredAbilityImpl {

    public VoraciousBibliophileTriggeredAbility() {
        super(Zone.BATTLEFIELD, new VoraciousBibliophileEffect(), false);
        setTriggerPhrase("Whenever you cast a spell with one or more targets, ");
    }

    private VoraciousBibliophileTriggeredAbility(final VoraciousBibliophileTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VoraciousBibliophileTriggeredAbility copy() {
        return new VoraciousBibliophileTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null) {
            return false;
        }
        int numTargets = spell.getStackAbility().getTargets().stream()
                .map(Target::getTargets)
                .mapToInt(Collection::size)
                .sum();
        if (numTargets > 0) {
            this.getEffects().setValue("numTargets", numTargets);
            return true;
        }
        return false;
    }
}

class VoraciousBibliophileEffect extends OneShotEffect {

    public VoraciousBibliophileEffect() {
        super(Outcome.DrawCard);
        staticText = "draw that many cards";
    }

    private VoraciousBibliophileEffect(final VoraciousBibliophileEffect effect) {
        super(effect);
    }

    @Override
    public VoraciousBibliophileEffect copy() {
        return new VoraciousBibliophileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int numTargets = (int) getValue("numTargets");
        controller.drawCards(numTargets, source, game);
        return true;
    }
}