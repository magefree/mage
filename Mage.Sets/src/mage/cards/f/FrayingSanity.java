package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class FrayingSanity extends CardImpl {

    public FrayingSanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of each end step, enchanted player puts the top X cards of their library into their graveyard, where X is the total number of cards put into their graveyard from anywhere this turn.
        this.addAbility(new FrayingSanityTriggeredAbility(), new CardsPutIntoGraveyardWatcher());

    }

    private FrayingSanity(final FrayingSanity card) {
        super(card);
    }

    @Override
    public FrayingSanity copy() {
        return new FrayingSanity(this);
    }
}

class FrayingSanityTriggeredAbility extends TriggeredAbilityImpl {

    public FrayingSanityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new FrayingSanityEffect());
    }

    public FrayingSanityTriggeredAbility(final FrayingSanityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FrayingSanityTriggeredAbility copy() {
        return new FrayingSanityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public String getRule() {
        return "At the beginning of each end step, enchanted player mills X cards, where X is the total number of cards put into their graveyard from anywhere this turn.";
    }
}

class FrayingSanityEffect extends OneShotEffect {

    int xAmount = 0;

    public FrayingSanityEffect() {
        super(Outcome.Detriment);
        this.staticText = "";
    }

    public FrayingSanityEffect(final FrayingSanityEffect effect) {
        super(effect);
    }

    @Override
    public FrayingSanityEffect copy() {
        return new FrayingSanityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }
        if (enchantment == null) {
            return false;
        }
        Player enchantedPlayer = game.getPlayer(enchantment.getAttachedTo());
        if (enchantedPlayer != null) {
            CardsPutIntoGraveyardWatcher watcher = game.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);
            if (watcher != null) {
                xAmount = watcher.getAmountCardsPutToGraveyard(enchantedPlayer.getId());
            }
            enchantedPlayer.millCards(xAmount, source, game);
            return true;
        }
        return false;
    }
}
