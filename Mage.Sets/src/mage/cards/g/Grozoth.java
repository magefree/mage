
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class Grozoth extends CardImpl {

    public Grozoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{U}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
                
        // When Grozoth enters the battlefield, you may search your library for any number of cards that have converted mana cost 9, reveal them, and put them into your hand. If you do, shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GrozothEffect(), true));
        
        // {4}: Grozoth loses defender until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD, 
                new LoseAbilitySourceEffect(DefenderAbility.getInstance(), Duration.EndOfTurn), 
                new ManaCostsImpl("{4}")));
        
        // Transmute {1}{U}{U}
        this.addAbility(new TransmuteAbility("{1}{U}{U}"));        
    }

    private Grozoth(final Grozoth card) {
        super(card);
    }

    @Override
    public Grozoth copy() {
        return new Grozoth(this);
    }
}

class GrozothEffect extends SearchEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, 9));
    }

    public GrozothEffect() {
        super(new TargetCardInLibrary(0, Integer.MAX_VALUE, filter), Outcome.DrawCard);
        staticText = "search your library for any number of cards that have mana value 9, reveal them, put them into your hand, then shuffle";
    }

    public GrozothEffect(final GrozothEffect effect) {
        super(effect);
    }

    @Override
    public GrozothEffect copy() {
        return new GrozothEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard != null && player != null && player.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                Cards cards = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = player.getLibrary().remove(cardId, game);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                player.revealCards(sourceCard.getIdName(), cards, game);
                player.moveCards(cards, Zone.HAND, source, game);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}