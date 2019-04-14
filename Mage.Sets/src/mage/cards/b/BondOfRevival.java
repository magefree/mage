package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BondOfRevival extends CardImpl {

    public BondOfRevival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Return target creature card from your graveyard to the battlefield. It gains haste until your next turn.
        this.getSpellAbility().addEffect(new BondOfRevivalEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
        ));
    }

    private BondOfRevival(final BondOfRevival card) {
        super(card);
    }

    @Override
    public BondOfRevival copy() {
        return new BondOfRevival(this);
    }
}

class BondOfRevivalEffect extends OneShotEffect {

    BondOfRevivalEffect() {
        super(Outcome.Benefit);
        staticText = "Return target creature card from your graveyard to the battlefield. " +
                "It gains haste until your next turn.";
    }

    private BondOfRevivalEffect(final BondOfRevivalEffect effect) {
        super(effect);
    }

    @Override
    public BondOfRevivalEffect copy() {
        return new BondOfRevivalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.UntilYourNextTurn);
        effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game) + 1));
        if (player.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            game.addEffect(effect, source);
        }
        return true;
    }
}


