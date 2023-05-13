package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class TarielReckonerOfSouls extends CardImpl {

    public TarielReckonerOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // {T}: Choose a creature card at random from target opponent's graveyard. Put that card onto the battlefield under your control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TarielReckonerOfSoulsEffect(), new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    private TarielReckonerOfSouls(final TarielReckonerOfSouls card) {
        super(card);
    }

    @Override
    public TarielReckonerOfSouls copy() {
        return new TarielReckonerOfSouls(this);
    }
}

class TarielReckonerOfSoulsEffect extends OneShotEffect {

    public TarielReckonerOfSoulsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Choose a creature card at random from target opponent's graveyard. Put that card onto the battlefield under your control";
    }

    public TarielReckonerOfSoulsEffect(final TarielReckonerOfSoulsEffect effect) {
        super(effect);
    }

    @Override
    public TarielReckonerOfSoulsEffect copy() {
        return new TarielReckonerOfSoulsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetOpponent != null) {
            Cards creatureCards = new CardsImpl();
            for (Card card : targetOpponent.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game)) {
                creatureCards.add(card);
            }
            if (!creatureCards.isEmpty()) {
                Card card = creatureCards.getRandom(game);
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
