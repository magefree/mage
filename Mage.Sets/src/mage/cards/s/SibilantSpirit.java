
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public final class SibilantSpirit extends CardImpl {

    public SibilantSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Sibilant Spirit attacks, defending player may draw a card.
        this.addAbility(new AttacksTriggeredAbility(new SibilantSpiritEffect(), false));
    }

    private SibilantSpirit(final SibilantSpirit card) {
        super(card);
    }

    @Override
    public SibilantSpirit copy() {
        return new SibilantSpirit(this);
    }
}

class SibilantSpiritEffect extends OneShotEffect {

    public SibilantSpiritEffect() {
        super(Outcome.DrawCard);
        staticText = "defending player may draw a card";
    }

    private SibilantSpiritEffect(final SibilantSpiritEffect effect) {
        super(effect);
    }

    @Override
    public SibilantSpiritEffect copy() {
        return new SibilantSpiritEffect(this);
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