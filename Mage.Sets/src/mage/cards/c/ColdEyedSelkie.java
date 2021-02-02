
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ColdEyedSelkie extends CardImpl {

    public ColdEyedSelkie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G/U}{G/U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
        // Whenever Cold-Eyed Selkie deals combat damage to a player, you may draw that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ColdEyeSelkieEffect(), true, true));

    }

    private ColdEyedSelkie(final ColdEyedSelkie card) {
        super(card);
    }

    @Override
    public ColdEyedSelkie copy() {
        return new ColdEyedSelkie(this);
    }
}

class ColdEyeSelkieEffect extends OneShotEffect {

    public ColdEyeSelkieEffect() {
        super(Outcome.DrawCard);
        this.staticText = "draw that many cards";
    }

    public ColdEyeSelkieEffect(final ColdEyeSelkieEffect effect) {
        super(effect);
    }

    @Override
    public ColdEyeSelkieEffect copy() {
        return new ColdEyeSelkieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.drawCards(amount, source, game);
                return true;
            }
        }
        return false;
    }
}
