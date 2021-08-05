package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElfWarriorToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReturnUponTheTide extends CardImpl {

    public ReturnUponTheTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Return target creature card from your graveyard to the battlefield. If it's an Elf, create two 1/1 green Elf Warrior creature tokens.
        this.getSpellAbility().addEffect(new ReturnUponTheTideEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // Foretell {3}{B}
        this.addAbility(new ForetellAbility(this, "{3}{B}"));
    }

    private ReturnUponTheTide(final ReturnUponTheTide card) {
        super(card);
    }

    @Override
    public ReturnUponTheTide copy() {
        return new ReturnUponTheTide(this);
    }
}

class ReturnUponTheTideEffect extends OneShotEffect {

    ReturnUponTheTideEffect() {
        super(Outcome.Benefit);
        staticText = "Return target creature card from your graveyard to the battlefield. " +
                "If it's an Elf, create two 1/1 green Elf Warrior creature tokens.";
    }

    private ReturnUponTheTideEffect(final ReturnUponTheTideEffect effect) {
        super(effect);
    }

    @Override
    public ReturnUponTheTideEffect copy() {
        return new ReturnUponTheTideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        if (permanent.hasSubtype(SubType.ELF, game)) {
            new ElfWarriorToken().putOntoBattlefield(2, game, source, player.getId());
        }
        return true;
    }
}
