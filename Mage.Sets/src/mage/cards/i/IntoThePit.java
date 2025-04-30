package mage.cards.i;

import mage.MageIdentifier;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntoThePit extends CardImpl {

    public IntoThePit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast spells from the top of your library by sacrificing a nonland permanent in addition to paying their other costs.
        this.addAbility(new SimpleStaticAbility(new IntoThePitEffect())
                .setIdentifier(MageIdentifier.IntoThePitAlternateCast));
    }

    private IntoThePit(final IntoThePit card) {
        super(card);
    }

    @Override
    public IntoThePit copy() {
        return new IntoThePit(this);
    }
}

class IntoThePitEffect extends AsThoughEffectImpl {

    IntoThePitEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.AIDontUseIt);
        staticText = "you may cast spells from the top of your library by sacrificing " +
                "a nonland permanent in addition to paying their other costs";
    }

    private IntoThePitEffect(final IntoThePitEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IntoThePitEffect copy() {
        return new IntoThePitEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card cardToCheck = game.getCard(objectId);
        if (cardToCheck == null
                || !source.isControlledBy(affectedControllerId)
                || !cardToCheck.isOwnedBy(affectedControllerId)) {
            return false;
        }
        Player player = game.getPlayer(cardToCheck.getOwnerId());
        if (player == null) {
            return false;
        }
        Card topCard = player.getLibrary().getFromTop(game);
        if (topCard == null
                || !topCard.getId().equals(cardToCheck.getMainCard().getId())
                || cardToCheck.isLand(game)) {
            return false;
        }

        Costs<Cost> newCosts = new CostsImpl<>();
        newCosts.add(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_NON_LAND));
        newCosts.addAll(cardToCheck.getSpellAbility().getCosts());
        player.setCastSourceIdWithAlternateMana(
                cardToCheck.getId(), cardToCheck.getManaCost(), newCosts,
                MageIdentifier.IntoThePitAlternateCast
        );
        return true;
    }
}
