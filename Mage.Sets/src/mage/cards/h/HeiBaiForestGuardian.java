package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.game.Game;
import mage.game.permanent.token.SpiritWorldToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeiBaiForestGuardian extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledEnchantmentPermanent();

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Legendary enchantments you control", xValue);

    public HeiBaiForestGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Hei Bai enters, reveal cards from the top of your library until you reveal a Shrine card. You may put that card onto the battlefield. Then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HeiBaiForestGuardianEffect()));

        // {W}{U}{B}{R}{G}, {T}: For each legendary enchantment you control, create a 1/1 colorless Spirit creature token with "This token can't block or be blocked by non-Spirit creatures."
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new SpiritWorldToken(), xValue)
                        .setText("for each legendary enchantment you control, create a 1/1 colorless Spirit creature " +
                                "token with \"This token can't block or be blocked by non-Spirit creatures.\""),
                new ManaCostsImpl<>("{W}{U}{B}{R}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(hint));
    }

    private HeiBaiForestGuardian(final HeiBaiForestGuardian card) {
        super(card);
    }

    @Override
    public HeiBaiForestGuardian copy() {
        return new HeiBaiForestGuardian(this);
    }
}

class HeiBaiForestGuardianEffect extends OneShotEffect {

    HeiBaiForestGuardianEffect() {
        super(Outcome.Benefit);
        staticText = "reveal cards from the top of your library until you reveal a Shrine card. " +
                "You may put that card onto the battlefield. Then shuffle.";
    }

    private HeiBaiForestGuardianEffect(final HeiBaiForestGuardianEffect effect) {
        super(effect);
    }

    @Override
    public HeiBaiForestGuardianEffect copy() {
        return new HeiBaiForestGuardianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card card = getCard(player, cards, game, source);
        player.revealCards(source, cards, game);
        if (card != null && player.chooseUse(
                Outcome.PutCardInPlay, "Put " + card.getName() + " onto the battlefield?", source, game
        )) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }

    private static Card getCard(Player player, Cards cards, Game game, Ability source) {
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.hasSubtype(SubType.SHRINE, game)) {
                return card;
            }
        }
        return null;
    }
}
