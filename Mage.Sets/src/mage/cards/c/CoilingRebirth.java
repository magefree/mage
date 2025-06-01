package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class CoilingRebirth extends CardImpl {

    public CoilingRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Gift a card
        this.addAbility(new GiftAbility(this, GiftType.CARD));

        // Return target creature card from your graveyard to the battlefield. Then if the gift was promised and that creature isn't legendary, create a token that's a copy of that creature, except it's 1/1.
        this.getSpellAbility().addEffect(new CoilingRebirthEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private CoilingRebirth(final CoilingRebirth card) {
        super(card);
    }

    @Override
    public CoilingRebirth copy() {
        return new CoilingRebirth(this);
    }
}

class CoilingRebirthEffect extends OneShotEffect {

    CoilingRebirthEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Return target creature card from your graveyard to the battlefield. Then if the gift was promised " +
                "and that creature isn't legendary, create a token that's a copy of that creature, except it's 1/1.";
    }

    private CoilingRebirthEffect(final CoilingRebirthEffect effect) {
        super(effect);
    }

    @Override
    public CoilingRebirthEffect copy() {
        return new CoilingRebirthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null || game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
            return false;
        }
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        if (GiftWasPromisedCondition.TRUE.apply(game, source)) {
            game.processAction();
            Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
            if (permanent != null && !permanent.isLegendary(game)) {
                CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                        null, null, false, 1, false, false,
                        null, 1, 1, false
                );
                effect.setTargetPointer(new FixedTarget(permanent, game));
                effect.apply(game, source);
            }
        }
        return true;
    }
}
