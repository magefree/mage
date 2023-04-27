package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class YurikoTheTigersShadow extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.NINJA, "a Ninja you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public YurikoTheTigersShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Commander ninjutsu {U}{B}
        this.addAbility(new NinjutsuAbility(new ManaCostsImpl<>("{U}{B}"), true));

        // Whenever a Ninja you control deals combat damage to a player, reveal the top card of your library and put that card into your hand. Each opponent loses life equal to that card's converted mana cost.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new YurikoTheTigersShadowEffect(), filter,
                false, SetTargetPointer.NONE, true
        ));
    }

    private YurikoTheTigersShadow(final YurikoTheTigersShadow card) {
        super(card);
    }

    @Override
    public YurikoTheTigersShadow copy() {
        return new YurikoTheTigersShadow(this);
    }
}

class YurikoTheTigersShadowEffect extends OneShotEffect {

    public YurikoTheTigersShadowEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal the top card of your library "
                + "and put that card into your hand. Each opponent loses life "
                + "equal to that card's mana value";
    }

    public YurikoTheTigersShadowEffect(final YurikoTheTigersShadowEffect effect) {
        super(effect);
    }

    @Override
    public YurikoTheTigersShadowEffect copy() {
        return new YurikoTheTigersShadowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards(source, new CardsImpl(card), game);
        player.moveCards(card, Zone.HAND, source, game);
        return new LoseLifeOpponentsEffect(
                card.getManaValue()
        ).apply(game, source);
    }
}
