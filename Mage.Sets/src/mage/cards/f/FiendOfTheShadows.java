package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetDiscard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class FiendOfTheShadows extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.HUMAN, "a Human");

    public FiendOfTheShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever Fiend of the Shadows deals combat damage to a player, that player exiles a card from their hand. You may play that card for as long as it remains exiled.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new FiendOfTheShadowsEffect(), false, true));

        // Sacrifice a Human: Regenerate Fiend of the Shadows.
        this.addAbility(new SimpleActivatedAbility(new RegenerateSourceEffect(), new SacrificeTargetCost(new TargetControlledPermanent(filter))));
    }

    private FiendOfTheShadows(final FiendOfTheShadows card) {
        super(card);
    }

    @Override
    public FiendOfTheShadows copy() {
        return new FiendOfTheShadows(this);
    }
}

class FiendOfTheShadowsEffect extends OneShotEffect {

    FiendOfTheShadowsEffect() {
        super(Outcome.Discard);
        staticText = "that player exiles a card from their hand. " +
                "You may play that card for as long as it remains exiled";
    }

    private FiendOfTheShadowsEffect(final FiendOfTheShadowsEffect effect) {
        super(effect);
    }

    @Override
    public FiendOfTheShadowsEffect copy() {
        return new FiendOfTheShadowsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        TargetCard targetCard = new TargetDiscard(player.getId());
        player.choose(outcome, targetCard, source.getSourceId(), game);
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCardToExileWithInfo(
                card, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source),
                source, game, Zone.HAND, true
        );
        CardUtil.makeCardPlayable(game, source, card, Duration.Custom, false);
        return true;
    }
}
