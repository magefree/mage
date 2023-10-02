
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class HarborGuardian extends CardImpl {

    public HarborGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Harbor Guardian attacks, defending player may draw a card.
        this.addAbility(new AttacksTriggeredAbility(new HarborGuardianEffect(), false));
    }

    private HarborGuardian(final HarborGuardian card) {
        super(card);
    }

    @Override
    public HarborGuardian copy() {
        return new HarborGuardian(this);
    }
}

class HarborGuardianEffect extends OneShotEffect {

    public HarborGuardianEffect() {
        super(Outcome.DrawCard);
        staticText = "defending player may draw a card";
    }

    private HarborGuardianEffect(final HarborGuardianEffect effect) {
        super(effect);
    }

    @Override
    public HarborGuardianEffect copy() {
        return new HarborGuardianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defenderId = game.getCombat().getDefenderId(source.getSourceId());
        Player defender = game.getPlayer(defenderId);
        if (defender != null) {
            if (defender.chooseUse(outcome, "Draw a card?", source, game)) {
                defender.drawCards(1, source, game);
            }
        }
        return false;
    }
}
