package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BrainstormEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HidetsuguAndKairi extends CardImpl {

    public HidetsuguAndKairi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Hidetsugu and Kairi enters the battlefield, draw three cards, then put two cards from your hand on top of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BrainstormEffect()));

        // When Hidetsugu and Kairi dies, exile the top card of your library. Target opponent loses life equal to its mana value. If it's an instant or sorcery card, you may cast it without paying its mana cost.
        Ability ability = new DiesSourceTriggeredAbility(new HidetsuguAndKairiEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private HidetsuguAndKairi(final HidetsuguAndKairi card) {
        super(card);
    }

    @Override
    public HidetsuguAndKairi copy() {
        return new HidetsuguAndKairi(this);
    }
}

class HidetsuguAndKairiEffect extends OneShotEffect {

    HidetsuguAndKairiEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. Target opponent loses life equal to its mana value. " +
                "If it's an instant or sorcery card, you may cast it without paying its mana cost";
    }

    private HidetsuguAndKairiEffect(final HidetsuguAndKairiEffect effect) {
        super(effect);
    }

    @Override
    public HidetsuguAndKairiEffect copy() {
        return new HidetsuguAndKairiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null || opponent == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null || !player.moveCardsToExile(card, source, game, true, null, "")) {
            return false;
        }
        opponent.loseLife(card.getManaValue(), game, source, false);
        return !card.isInstantOrSorcery(game) || CardUtil.castSpellWithAttributesForFree(player, source, game, card);
    }
}
