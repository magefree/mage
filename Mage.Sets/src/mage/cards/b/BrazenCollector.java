package mage.cards.b;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrazenCollector extends CardImpl {

    public BrazenCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Brazen Collector attacks, add {R}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new AttacksTriggeredAbility(new BirgiGodOfStorytellingManaEffect()));
    }

    private BrazenCollector(final BrazenCollector card) {
        super(card);
    }

    @Override
    public BrazenCollector copy() {
        return new BrazenCollector(this);
    }
}

class BrazenCollectorEffect extends OneShotEffect {

    BrazenCollectorEffect() {
        super(Outcome.Benefit);
        staticText = "add {R}. Until end of turn, you don't lose this mana as steps and phases end";
    }

    private BrazenCollectorEffect(final BrazenCollectorEffect effect) {
        super(effect);
    }

    @Override
    public BrazenCollectorEffect copy() {
        return new BrazenCollectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.getManaPool().addMana(new Mana(ManaType.RED, 1), game, source, true);
        return true;
    }
}
