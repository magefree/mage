package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Bookwurm extends CardImpl {

    public Bookwurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Bookwurm enters the battlefield, you gain 3 life and draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // {2}{G}: Put Bookwurm from your graveyard into your library third from the top.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new BookwurmEffect(), new ManaCostsImpl<>("{2}{G}")));
    }

    private Bookwurm(final Bookwurm card) {
        super(card);
    }

    @Override
    public Bookwurm copy() {
        return new Bookwurm(this);
    }
}

class BookwurmEffect extends OneShotEffect {

    BookwurmEffect() {
        super(Outcome.Benefit);
        staticText = "put {this} from your graveyard into your library third from the top";
    }

    private BookwurmEffect(final BookwurmEffect effect) {
        super(effect);
    }

    @Override
    public BookwurmEffect copy() {
        return new BookwurmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        return player != null
                && sourceObject instanceof Card
                && player.putCardOnTopXOfLibrary(
                (Card) sourceObject, game, source, 3, true
        );
    }
}
