
package mage.cards.t;

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
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class TormentOfScarabs extends CardImpl {

    public TormentOfScarabs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.LoseLife));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of enchanted player's upkeep, that player loses 3 life unless he or she sacrifices a nonland permanent or discards a card.
        this.addAbility(new TormentOfScarabsAbility());
    }

    public TormentOfScarabs(final TormentOfScarabs card) {
        super(card);
    }

    @Override
    public TormentOfScarabs copy() {
        return new TormentOfScarabs(this);
    }
}

class TormentOfScarabsAbility extends TriggeredAbilityImpl {

    public TormentOfScarabsAbility() {
        super(Zone.BATTLEFIELD, new TormentOfScarabsEffect());
    }

    public TormentOfScarabsAbility(final TormentOfScarabsAbility ability) {
        super(ability);
    }

    @Override
    public TormentOfScarabsAbility copy() {
        return new TormentOfScarabsAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            if (game.isActivePlayer(enchantment.getAttachedTo())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of enchanted player's upkeep, that player loses 3 life unless he or she sacrifices a nonland permanent or discards a card.";
    }

}

class TormentOfScarabsEffect extends OneShotEffect {

    public TormentOfScarabsEffect() {
        super(Outcome.LoseLife);
        this.staticText = "that player loses 3 life unless he or she sacrifices a nonland permanent or discards a card";
    }

    public TormentOfScarabsEffect(final TormentOfScarabsEffect effect) {
        super(effect);
    }

    @Override
    public TormentOfScarabsEffect copy() {
        return new TormentOfScarabsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }
        Player enchantedPlayer = game.getPlayer(enchantment.getAttachedTo());
        if (enchantedPlayer != null) {
            int permanents = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_NON_LAND, enchantedPlayer.getId(), game);
            if (permanents > 0 && enchantedPlayer.chooseUse(outcome, "Sacrifice a nonland permanent?",
                    "Otherwise you have to discard a card or lose 3 life.", "Sacrifice", "Discard or life loss", source, game)) {
                Target target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND);
                if (enchantedPlayer.choose(outcome, target, source.getSourceId(), game)) {
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        permanent.sacrifice(source.getSourceId(), game);
                        return true;
                    }
                }
            }
            if (!enchantedPlayer.getHand().isEmpty() && enchantedPlayer.chooseUse(outcome, "Discard a card?",
                    "Otherwise you lose 3 life.", "Discard", "Lose 3 life", source, game)) {
                enchantedPlayer.discardOne(false, source, game);
                return true;
            }
            enchantedPlayer.loseLife(3, game, false);
            return true;
        }

        return false;
    }
}
