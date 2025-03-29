package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;
import mage.game.permanent.token.ZombieDruidToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TevalsJudgement extends CardImpl {

    public TevalsJudgement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Whenever one or more cards leave your graveyard, choose one that hasn’t been chosen this turn --
        // * Draw a card.
        Ability ability = new CardsLeaveGraveyardTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
        );
        ability.getModes().setLimitUsageByOnce(true);

        // * Create a Treasure token.
        ability.addMode(new Mode(new CreateTokenEffect(new TreasureToken())));

        // * Create a 2/2 black Zombie Druid creature token.
        ability.addMode(new Mode(new CreateTokenEffect(new ZombieDruidToken())));
        this.addAbility(ability);
    }

    private TevalsJudgement(final TevalsJudgement card) {
        super(card);
    }

    @Override
    public TevalsJudgement copy() {
        return new TevalsJudgement(this);
    }
}
