package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RampagingGrowth extends CardImpl {

    public RampagingGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Search your library for a basic land card, put it on the battlefield, then shuffle. Until end of turn, that land becomes a 4/3 Insect creature with reach and haste. It's still a land.
        this.getSpellAbility().addEffect(new RampagingGrowthEffect());
    }

    private RampagingGrowth(final RampagingGrowth card) {
        super(card);
    }

    @Override
    public RampagingGrowth copy() {
        return new RampagingGrowth(this);
    }
}

class RampagingGrowthEffect extends OneShotEffect {

    RampagingGrowthEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a basic land card, put it on the battlefield, then shuffle. " +
                "Until end of turn, that land becomes a 4/3 Insect creature with reach and haste. It's still a land";
    }

    private RampagingGrowthEffect(final RampagingGrowthEffect effect) {
        super(effect);
    }

    @Override
    public RampagingGrowthEffect copy() {
        return new RampagingGrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND_A);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getTargetController(), game);
        if (card == null) {
            player.shuffleLibrary(source, game);
            return true;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null) {
            game.addEffect(new BecomesCreatureTargetEffect(
                    new CreatureToken(4, 3, "", SubType.INSECT
                    ).withAbility(ReachAbility.getInstance()).withAbility(HasteAbility.getInstance()),
                    false, true, Duration.EndOfTurn
            ).withDurationRuleAtStart(true).setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
