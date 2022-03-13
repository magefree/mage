package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlowsporeShaman extends CardImpl {

    public GlowsporeShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Glowspore Shaman enters the battlefield, put the top three cards of your library into your graveyard. You may put a land card from your graveyard on top of your library.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new MillCardsControllerEffect(3), false
        );
        ability.addEffect(new GlowsporeShamanEffect());
        this.addAbility(ability);
    }

    private GlowsporeShaman(final GlowsporeShaman card) {
        super(card);
    }

    @Override
    public GlowsporeShaman copy() {
        return new GlowsporeShaman(this);
    }
}

class GlowsporeShamanEffect extends OneShotEffect {

    public static final FilterLandCard filter
            = new FilterLandCard("a land card from your graveyard");

    public GlowsporeShamanEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may put a land card from your graveyard "
                + "on top of your library.";
    }

    public GlowsporeShamanEffect(final GlowsporeShamanEffect effect) {
        super(effect);
    }

    @Override
    public GlowsporeShamanEffect copy() {
        return new GlowsporeShamanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetCardInYourGraveyard(0, 1, filter, true);
        if (player.chooseUse(outcome, "Put a land card on top of your library?", source, game)
                && player.choose(outcome, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                return player.putCardsOnTopOfLibrary(new CardsImpl(card), game, source, false);
            }
        }
        return true;
    }
}
