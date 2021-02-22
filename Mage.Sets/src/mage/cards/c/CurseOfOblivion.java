package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class CurseOfOblivion extends CardImpl {

    public CurseOfOblivion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer target = new TargetPlayer();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(target.getTargetName());
        this.addAbility(ability);

        // At the beginning of enchanted player's upkeep, that player exiles two cards from their graveyard.
        this.addAbility(new CurseOfOblivionAbility());
    }

    private CurseOfOblivion(final CurseOfOblivion card) {
        super(card);
    }

    @Override
    public CurseOfOblivion copy() {
        return new CurseOfOblivion(this);
    }
}

class CurseOfOblivionAbility extends TriggeredAbilityImpl {

    CurseOfOblivionAbility() {
        super(Zone.BATTLEFIELD, new ExileFromZoneTargetEffect(Zone.GRAVEYARD, StaticFilters.FILTER_CARD_CARDS, 2, false));
    }

    private CurseOfOblivionAbility(final CurseOfOblivionAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfOblivionAbility copy() {
        return new CurseOfOblivionAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = getSourcePermanentOrLKI(game);
        if (enchantment == null || !game.isActivePlayer(enchantment.getAttachedTo())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(enchantment.getAttachedTo()));
        return true;
    }

    @Override
    public String getRule() {
        return "At the beginning of enchanted player's upkeep, that player exiles two cards from their graveyard.";
    }
}
