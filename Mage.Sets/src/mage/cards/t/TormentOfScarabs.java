package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
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
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of enchanted player's upkeep, that player loses 3 life unless they sacrifice a nonland permanent or discards a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TormentOfScarabsEffect(), TargetController.ENCHANTED, false
        ));
    }

    private TormentOfScarabs(final TormentOfScarabs card) {
        super(card);
    }

    @Override
    public TormentOfScarabs copy() {
        return new TormentOfScarabs(this);
    }
}

class TormentOfScarabsEffect extends OneShotEffect {

    TormentOfScarabsEffect() {
        super(Outcome.LoseLife);
        this.staticText = "that player loses 3 life unless they sacrifice a nonland permanent or discard a card";
    }

    private TormentOfScarabsEffect(final TormentOfScarabsEffect effect) {
        super(effect);
    }

    @Override
    public TormentOfScarabsEffect copy() {
        return new TormentOfScarabsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player enchantedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (enchantedPlayer == null) {
            return false;
        }
        int permanents = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_NON_LAND, enchantedPlayer.getId(), game);
        if (permanents > 0 && enchantedPlayer.chooseUse(outcome, "Sacrifice a nonland permanent?",
                "Otherwise you have to discard a card or lose 3 life.", "Sacrifice", "Discard or life loss", source, game)) {
            Target target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND);
            if (enchantedPlayer.choose(outcome, target, source, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.sacrifice(source, game);
                    return true;
                }
            }
        }
        if (!enchantedPlayer.getHand().isEmpty() && enchantedPlayer.chooseUse(outcome, "Discard a card?",
                "Otherwise you lose 3 life.", "Discard", "Lose 3 life", source, game)) {
            enchantedPlayer.discardOne(false, false, source, game);
            return true;
        }
        enchantedPlayer.loseLife(3, game, source, false);
        return true;
    }
}
