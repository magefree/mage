package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Lychguard extends CardImpl {

    public Lychguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Guardian Patrols -- {3}{B}, Sacrifice Lychguard: Return all legendary creature cards from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(new LychguardEffect(), new ManaCostsImpl<>("{3}{B}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private Lychguard(final Lychguard card) {
        super(card);
    }

    @Override
    public Lychguard copy() {
        return new Lychguard(this);
    }
}

class LychguardEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    LychguardEffect() {
        super(Outcome.Benefit);
        staticText = "return all legendary creature cards from your graveyard to your hand";
    }

    private LychguardEffect(final LychguardEffect effect) {
        super(effect);
    }

    @Override
    public LychguardEffect copy() {
        return new LychguardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getGraveyard().getCards(filter, game));
        return player.moveCards(cards, Zone.HAND, source, game);
    }
}
