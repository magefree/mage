
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class BalthorTheDefiled extends CardImpl {

    public BalthorTheDefiled(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE, SubType.DWARF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Minion creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield,
                new FilterCreaturePermanent(SubType.MINION, "Minion creatures"), false)));

        // {B}{B}{B}, Exile Balthor the Defiled: Each player returns all black and all red creature cards from their graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BalthorTheDefiledEffect(), new ManaCostsImpl<>("{B}{B}{B}"));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);

    }

    private BalthorTheDefiled(final BalthorTheDefiled card) {
        super(card);
    }

    @Override
    public BalthorTheDefiled copy() {
        return new BalthorTheDefiled(this);
    }
}

class BalthorTheDefiledEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard();

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.RED)));
    }

    public BalthorTheDefiledEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player returns all black and all red creature cards from their graveyard to the battlefield";
    }

    public BalthorTheDefiledEffect(final BalthorTheDefiledEffect effect) {
        super(effect);
    }

    @Override
    public BalthorTheDefiledEffect copy() {
        return new BalthorTheDefiledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToReturn = new CardsImpl();
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    cardsToReturn.addAllCards(player.getGraveyard().getCards(filter, source.getControllerId(), source, game));
                }
            }
            controller.moveCards(cardsToReturn.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            return true;
        }
        return false;
    }
}
