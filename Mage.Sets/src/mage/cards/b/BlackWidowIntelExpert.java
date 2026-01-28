package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class BlackWidowIntelExpert extends CardImpl {

    public BlackWidowIntelExpert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Black Widow deals combat damage to an opponent, you and that player each draw two cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new BlackWidowEffect(), false, true));

    }

    private BlackWidowIntelExpert(final BlackWidowIntelExpert card) {
        super(card);
    }

    @Override
    public BlackWidowIntelExpert copy() {
        return new BlackWidowIntelExpert(this);
    }
}

class BlackWidowEffect extends OneShotEffect {

    BlackWidowEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you and that player each draw two cards";
    }

    private BlackWidowEffect(final BlackWidowEffect effect) {
        super(effect);
    }

    @Override
    public BlackWidowEffect copy() {
        return new BlackWidowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourceController = game.getPlayer(source.getControllerId());
        Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (sourceController != null && damagedPlayer != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                sourceController.drawCards(2, source, game);
                damagedPlayer.drawCards(2, source, game);
                return true;
            }
        }
        return false;
    }
}
