package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.Shapeshifter32Token;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlackMarketConnections extends CardImpl {

    public BlackMarketConnections(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // At the beginning of your precombat main phase, choose one or more —
        // • Sell Contraband — Create a Treasure token. You lose 1 life.
        Ability ability = new BeginningOfPreCombatMainTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), TargetController.YOU, false
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1));
        ability.withFirstModeFlavorWord("Sell Contraband");
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(3);

        // • Buy Information — Draw a card. You lose 2 life.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1))
                .addEffect(new LoseLifeSourceControllerEffect(2))
                .withFlavorWord("Buy Information"));

        // • Hire a Mercenary — Create a 3/2 colorless Shapeshifter creature token with changeling. You lose 3 life.
        ability.addMode(new Mode(new CreateTokenEffect(new Shapeshifter32Token()))
                .addEffect(new LoseLifeSourceControllerEffect(3))
                .withFlavorWord("Hire a Mercenary"));
        this.addAbility(ability);
    }

    private BlackMarketConnections(final BlackMarketConnections card) {
        super(card);
    }

    @Override
    public BlackMarketConnections copy() {
        return new BlackMarketConnections(this);
    }
}
