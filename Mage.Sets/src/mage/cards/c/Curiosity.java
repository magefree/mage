
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Alvin, noxx
 */
public final class Curiosity extends CardImpl {

    public Curiosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Neutral));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature deals damage to an opponent, you may draw a card.
        this.addAbility(new CuriosityAbility());
    }

    private Curiosity(final Curiosity card) {
        super(card);
    }

    @Override
    public Curiosity copy() {
        return new Curiosity(this);
    }
}


class CuriosityAbility extends TriggeredAbilityImpl {

    public CuriosityAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    public CuriosityAbility(final CuriosityAbility ability) {
        super(ability);
    }

    @Override
    public CuriosityAbility copy() {
        return new CuriosityAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null && game.getOpponents(this.controllerId).contains(event.getTargetId()) && permanent.getAttachments().contains(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature deals damage to an opponent, you may draw a card.";
    }
}
