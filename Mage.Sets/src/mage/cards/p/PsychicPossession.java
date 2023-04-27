
package mage.cards.p;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;

/**
 *
 * @author spjspj
 */
public final class PsychicPossession extends CardImpl {

    public PsychicPossession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant opponent
        TargetPlayer auraTarget = new TargetOpponent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.DrawCard));
        this.addAbility(new EnchantAbility(auraTarget));

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect()));

        // Whenever enchanted opponent draws a card, you may draw a card.
        this.addAbility(new PsychicPossessionTriggeredAbility());
    }

    private PsychicPossession(final PsychicPossession card) {
        super(card);
    }

    @Override
    public PsychicPossession copy() {
        return new PsychicPossession(this);
    }
}

class PsychicPossessionTriggeredAbility extends TriggeredAbilityImpl {

    public PsychicPossessionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    public PsychicPossessionTriggeredAbility(final PsychicPossessionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PsychicPossessionTriggeredAbility copy() {
        return new PsychicPossessionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.sourceId);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Player opponent = game.getPlayer(enchantment.getAttachedTo());
            Player player = game.getPlayer(event.getPlayerId());
            if (opponent != null && player != null && Objects.equals(player, opponent)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted opponent draws a card, you may draw a card";
    }

}
