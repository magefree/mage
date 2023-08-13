package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class UndercityInformer extends CardImpl {

    public UndercityInformer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        //{1}, Sacrifice a creature: Target player reveals the top card of their library until they reveal a land card, then puts those cards into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UndercityInformerEffect(), new ManaCostsImpl<>("{1}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private UndercityInformer(final UndercityInformer card) {
        super(card);
    }

    @Override
    public UndercityInformer copy() {
        return new UndercityInformer(this);
    }
}

class UndercityInformerEffect extends OneShotEffect {

    public UndercityInformerEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Target player reveals the top card of their library until they reveal a land card, then puts those cards into their graveyard";
    }

    public UndercityInformerEffect(final UndercityInformerEffect effect) {
        super(effect);
    }

    @Override
    public UndercityInformerEffect copy() {
        return new UndercityInformerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.isLand(game)) {
                break;
            }
        }
        player.revealCards(source, cards, game);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}
