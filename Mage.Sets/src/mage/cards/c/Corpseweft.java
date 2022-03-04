package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.CorpseweftZombieToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class Corpseweft extends CardImpl {

    public Corpseweft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // {1}{B}, Exile one or more creature cards from your graveyard: Create a tapped X/X black Zombie Horror creature token, where X is twice the number of cards exiled this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CorpseweftEffect(), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(1, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD)));
        this.addAbility(ability);
    }

    private Corpseweft(final Corpseweft card) {
        super(card);
    }

    @Override
    public Corpseweft copy() {
        return new Corpseweft(this);
    }
}

class CorpseweftEffect extends OneShotEffect {

    public CorpseweftEffect() {
        super(Outcome.Benefit);
        this.staticText = "create a tapped X/X black Zombie Horror creature token, where X is twice the number of cards exiled this way";
    }

    public CorpseweftEffect(final CorpseweftEffect effect) {
        super(effect);
    }

    @Override
    public CorpseweftEffect copy() {
        return new CorpseweftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof ExileFromGraveCost) {
                    amount = ((ExileFromGraveCost) cost).getExiledCards().size() * 2;
                    new CreateTokenEffect(new CorpseweftZombieToken(amount, amount), 1, true, false).apply(game, source);
                }
            }
            if (amount > 0) {

            }
        }
        return false;
    }
}
