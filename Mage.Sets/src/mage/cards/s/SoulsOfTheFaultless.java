
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.IsDealtCombatDamageSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author North
 */
public final class SoulsOfTheFaultless extends CardImpl {

    public SoulsOfTheFaultless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Whenever Souls of the Faultless is dealt combat damage, you gain that much life and attacking player loses that much life.
        this.addAbility(new IsDealtCombatDamageSourceTriggeredAbility(
                new SoulsOfTheFaultlessEffect()
        ));
    }

    private SoulsOfTheFaultless(final SoulsOfTheFaultless card) {
        super(card);
    }

    @Override
    public SoulsOfTheFaultless copy() {
        return new SoulsOfTheFaultless(this);
    }
}

class SoulsOfTheFaultlessEffect extends OneShotEffect {

    SoulsOfTheFaultlessEffect() {
        super(Outcome.GainLife);
        staticText = "you gain that much life and attacking player loses that much life";
    }

    private SoulsOfTheFaultlessEffect(final SoulsOfTheFaultlessEffect effect) {
        super(effect);
    }

    @Override
    public SoulsOfTheFaultlessEffect copy() {
        return new SoulsOfTheFaultlessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = SavedDamageValue.MUCH.calculate(game, source, this);

        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(amount, game, source);
        }

        UUID attackerId = game.getActivePlayerId();
        Player attacker = game.getPlayer(attackerId);
        if (attacker != null) {
            attacker.loseLife(amount, game, source, false);
        }
        return true;
    }
}
