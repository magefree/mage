package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DeadMansChest extends CardImpl {

    public DeadMansChest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature an opponent controls
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted creature dies, exile cards equal to its power from the top of its owner's library. You may cast nonland cards from among them as long as they remain exiled, and you may spend mana as though it were mana of any type to cast those spells.
        this.addAbility(new DiesAttachedTriggeredAbility(new DeadMansChestEffect(), "enchanted creature", false));
    }

    private DeadMansChest(final DeadMansChest card) {
        super(card);
    }

    @Override
    public DeadMansChest copy() {
        return new DeadMansChest(this);
    }
}

class DeadMansChestEffect extends OneShotEffect {

    public DeadMansChestEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile cards equal to its power from the top of its owner's library. "
                + "You may cast spells from among those cards for as long as they remain exiled, "
                + "and you may spend mana as though it were mana of any type to cast those spells";
    }

    public DeadMansChestEffect(final DeadMansChestEffect effect) {
        super(effect);
    }

    @Override
    public DeadMansChestEffect copy() {
        return new DeadMansChestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Permanent attachedTo = (Permanent) getValue("attachedTo");
        if (controller != null && sourceObject != null && attachedTo != null) {
            Player owner = game.getPlayer(attachedTo.getOwnerId());
            int amount = attachedTo.getPower().getValue();
            if (owner != null && amount > 0) {
                Set<Card> cards = owner.getLibrary().getTopCards(game, amount);
                CardUtil.exileCardsAndMakeCastable(game, source, cards, Duration.EndOfGame,
                    CardUtil.CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE, null, null);
            }
            return true;
        }
        return false;
    }
}