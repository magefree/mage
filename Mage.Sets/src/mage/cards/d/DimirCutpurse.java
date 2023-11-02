package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DimirCutpurse extends CardImpl {

    public DimirCutpurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Dimir Cutpurse deals combat damage to a player, that player discards a card and you draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DimirCutpurseEffect(), false, true));
    }

    private DimirCutpurse(final DimirCutpurse card) {
        super(card);
    }

    @Override
    public DimirCutpurse copy() {
        return new DimirCutpurse(this);
    }
}

class DimirCutpurseEffect extends OneShotEffect {

    DimirCutpurseEffect() {
        super(Outcome.Neutral);
        staticText = "that player discards a card and you draw a card";
    }

    private DimirCutpurseEffect(final DimirCutpurseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (damagedPlayer != null) {
            damagedPlayer.discard(1, false, false, source, game);
        }
        if (you != null) {
            you.drawCards(1, source, game);
        }
        return true;
    }

    @Override
    public DimirCutpurseEffect copy() {
        return new DimirCutpurseEffect(this);
    }
}
