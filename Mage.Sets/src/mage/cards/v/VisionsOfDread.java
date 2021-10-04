package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.costs.costadjusters.CommanderManaValueAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VisionsOfDread extends CardImpl {

    public VisionsOfDread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent puts a creature card of their choice from their graveyard onto the battlefield under your control.
        this.getSpellAbility().addEffect(new VisionsOfDreadEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Flashback {8}{B}{B}. This spell costs {X} less to cast this way, where X is the greatest mana value of a commander you own on the battlefield or in the command zone.
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{8}{B}{B}"))
                .setAbilityName("This spell costs {X} less to cast this way, where X is the greatest mana value " +
                        "of a commander you own on the battlefield or in the command zone.")
                .setCostAdjuster(CommanderManaValueAdjuster.instance));
    }

    private VisionsOfDread(final VisionsOfDread card) {
        super(card);
    }

    @Override
    public VisionsOfDread copy() {
        return new VisionsOfDread(this);
    }
}

class VisionsOfDreadEffect extends OneShotEffect {

    VisionsOfDreadEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent puts a creature card of their choice " +
                "from their graveyard onto the battlefield under your control";
    }

    private VisionsOfDreadEffect(final VisionsOfDreadEffect effect) {
        super(effect);
    }

    @Override
    public VisionsOfDreadEffect copy() {
        return new VisionsOfDreadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null
                || opponent.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) < 1) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
        );
        target.setNotTarget(true);
        opponent.choose(Outcome.Detriment, target, source.getSourceId(), game);
        return controller.moveCards(game.getCard(target.getFirstTarget()), Zone.BATTLEFIELD, source, game);
    }
}
