package mage.cards.g;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ForMirrodinAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GlimmerLens extends CardImpl {

    public GlimmerLens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");
        this.subtype.add(SubType.EQUIPMENT);

        // For Mirrodin!
        this.addAbility(new ForMirrodinAbility());

        // Whenever equipped creature and at least one other creature attack, draw a card.
        this.addAbility(new GlimmerLensTriggeredAbility());

        // Equip {1}{W}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{1}{W}"), false));
    }

    private GlimmerLens(final GlimmerLens card) {
        super(card);
    }

    @Override
    public GlimmerLens copy() {
        return new GlimmerLens(this);
    }
}

class GlimmerLensTriggeredAbility extends TriggeredAbilityImpl {

    GlimmerLensTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        setTriggerPhrase("Whenever equipped creature and at least one other creature attack, ");
    }

    private GlimmerLensTriggeredAbility(final GlimmerLensTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GlimmerLensTriggeredAbility copy() {
        return new GlimmerLensTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attachment = getSourcePermanentOrLKI(game);
        UUID equippedCreature = attachment.getAttachedTo();
        return game.getCombat().getAttackers().contains(equippedCreature)
                && game
                .getCombat()
                .getAttackers()
                .stream()
                .filter(uuid -> !equippedCreature.equals(uuid))
                .map(game::getPermanent)
                .anyMatch(Objects::nonNull);
    }
}
