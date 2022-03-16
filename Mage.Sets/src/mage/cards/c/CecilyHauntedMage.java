package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.keyword.FriendsForeverAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CecilyHauntedMage extends CardImpl {

    public CecilyHauntedMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Your maximum hand size is eleven.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                11, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // Whenever Eleven, the Mage attacks, you draw a card and you lose 1 life. Then if you have eleven or more cards in your hand, you may cast an instant or sorcery spell from your hand without paying its mana cost.
        this.addAbility(new AttacksTriggeredAbility(new CecilyHauntedMageEffect()));

        // Friends forever
        this.addAbility(FriendsForeverAbility.getInstance());
    }

    private CecilyHauntedMage(final CecilyHauntedMage card) {
        super(card);
    }

    @Override
    public CecilyHauntedMage copy() {
        return new CecilyHauntedMage(this);
    }
}

class CecilyHauntedMageEffect extends OneShotEffect {

    CecilyHauntedMageEffect() {
        super(Outcome.Benefit);
        staticText = "you draw a card and you lose 1 life. Then if you have eleven or more cards in your hand, " +
                "you may cast an instant or sorcery spell from your hand without paying its mana cost";
    }

    private CecilyHauntedMageEffect(final CecilyHauntedMageEffect effect) {
        super(effect);
    }

    @Override
    public CecilyHauntedMageEffect copy() {
        return new CecilyHauntedMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(1, source, game);
        player.loseLife(1, game, source, false);
        return player.getHand().size() < 11
                || CardUtil.castSpellWithAttributesForFree(
                player, source, game, player.getHand().copy(),
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY
        );
    }
}
