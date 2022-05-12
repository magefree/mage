package mage.cards.g;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiftOfTheWoods extends CardImpl {

    public GiftOfTheWoods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.getSpellAbility().addTarget(auraTarget);
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever enchanted creature blocks or becomes blocked, it gets +0/+3 until end of turn and you gain 1 life.
        this.addAbility(new GiftOfTheWoodsTriggeredAbility());
    }

    private GiftOfTheWoods(final GiftOfTheWoods card) {
        super(card);
    }

    @Override
    public GiftOfTheWoods copy() {
        return new GiftOfTheWoods(this);
    }
}

class GiftOfTheWoodsTriggeredAbility extends TriggeredAbilityImpl {

    GiftOfTheWoodsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostEnchantedEffect(0, 3, Duration.EndOfTurn));
        this.addEffect(new GainLifeEffect(1));
    }

    private GiftOfTheWoodsTriggeredAbility(final GiftOfTheWoodsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GiftOfTheWoodsTriggeredAbility copy() {
        return new GiftOfTheWoodsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.getAttachedTo() != null
                && (event.getSourceId().equals(permanent.getAttachedTo())
                || event.getTargetId().equals(permanent.getAttachedTo()));
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature blocks or becomes blocked, " +
                "it gets +0/+3 until end of turn and you gain 1 life.";
    }
}
