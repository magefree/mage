
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EternalizeAbility;
import mage.abilities.keyword.MenaceAbility;
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
public final class Dreamstealer extends CardImpl {

    public Dreamstealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Dreamstealer deals combat damage to a player, that player discards that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DreamstealerDiscardEffect(), false, true));

        // Eternalize {4}{B}{B}
        this.addAbility(new EternalizeAbility(new ManaCostsImpl("{4}{B}{B}"), this));

    }

    private Dreamstealer(final Dreamstealer card) {
        super(card);
    }

    @Override
    public Dreamstealer copy() {
        return new Dreamstealer(this);
    }
}

class DreamstealerDiscardEffect extends OneShotEffect {

    public DreamstealerDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "that player discards that many cards";
    }

    public DreamstealerDiscardEffect(final DreamstealerDiscardEffect effect) {
        super(effect);
    }

    @Override
    public DreamstealerDiscardEffect copy() {
        return new DreamstealerDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            int damage = (Integer) getValue("damage");
            targetPlayer.discard(damage, false, false, source, game);
            game.informPlayers(targetPlayer.getLogName() + "discards " + damage + " card(s)");
            return true;
        }
        return false;
    }

}
