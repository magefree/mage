package mage.cards.c;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ColossalPlow extends CardImpl {

    public ColossalPlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // Whenever Colossal Plow attacks, add {W}{W}{W} and you gain 3 life. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new AttacksTriggeredAbility(new ColossalPlowEffect(), false));

        // Crew 6
        this.addAbility(new CrewAbility(6));
    }

    private ColossalPlow(final ColossalPlow card) {
        super(card);
    }

    @Override
    public ColossalPlow copy() {
        return new ColossalPlow(this);
    }
}

class ColossalPlowEffect extends OneShotEffect {

    ColossalPlowEffect() {
        super(Outcome.Benefit);
        staticText = "add {W}{W}{W} and you gain 3 life. Until end of turn, " +
                "you don't lose this mana as steps and phases end";
    }

    private ColossalPlowEffect(final ColossalPlowEffect effect) {
        super(effect);
    }

    @Override
    public ColossalPlowEffect copy() {
        return new ColossalPlowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.getManaPool().addMana(Mana.WhiteMana(3), game, source, true);
        player.gainLife(3, game, source);
        return true;
    }
}
