package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.*;

/**
 * @author TheElk801
 */
public final class OracleOfTheAlpha extends CardImpl {

    public OracleOfTheAlpha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Oracle of the Alpha enters the battlefield, conjure the Power Nine into your library, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OracleOfTheAlphaEffect()));

        // Whenever Oracle of the Alpha attacks, scry 1.
        this.addAbility(new AttacksTriggeredAbility(new ScryEffect(1, false)));
    }

    private OracleOfTheAlpha(final OracleOfTheAlpha card) {
        super(card);
    }

    @Override
    public OracleOfTheAlpha copy() {
        return new OracleOfTheAlpha(this);
    }
}

class OracleOfTheAlphaEffect extends OneShotEffect {

    private static final List<String> power9 = Arrays.asList(
            "Ancestral Recall",
            "Black Lotus",
            "Mox Emerald",
            "Mox Jet",
            "Mox Pearl",
            "Mox Ruby",
            "Timetwister",
            "Time Walk"
    );

    OracleOfTheAlphaEffect() {
        super(Outcome.Benefit);
        staticText = "conjure the Power Nine into your library, then shuffle";
    }

    private OracleOfTheAlphaEffect(final OracleOfTheAlphaEffect effect) {
        super(effect);
    }

    @Override
    public OracleOfTheAlphaEffect copy() {
        return new OracleOfTheAlphaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Card> cards = new HashSet<>();
        for (String cardName : power9) {
            CardRepository
                    .instance
                    .findCards(new CardCriteria().setCodes("LEA").name(cardName))
                    .stream()
                    .findFirst()
                    .map(CardInfo::createCard)
                    .ifPresent(cards::add);
        }
        game.loadCards(cards, source.getControllerId());
        player.moveCards(cards, Zone.LIBRARY, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
