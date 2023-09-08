package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author noahg
 */
public final class PlatedPegasus extends CardImpl {

    public PlatedPegasus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.PEGASUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If a spell would deal damage to a permanent or player, prevent 1 damage that spell would deal to that permanent or player.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlatedPegasusEffect()));
    }

    private PlatedPegasus(final PlatedPegasus card) {
        super(card);
    }

    @Override
    public PlatedPegasus copy() {
        return new PlatedPegasus(this);
    }
}

class PlatedPegasusEffect extends PreventionEffectImpl {

    public PlatedPegasusEffect() {
        super(Duration.WhileOnBattlefield, 1, false, false);
        staticText = "If a spell would deal damage to a permanent or player, prevent 1 damage that spell would deal to that permanent or player.";
    }

    private PlatedPegasusEffect(final PlatedPegasusEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject == null) {
            stackObject = (StackObject) game.getLastKnownInformation(event.getSourceId(), Zone.STACK);
        }
        if (stackObject instanceof Spell) {
            return super.applies(event, source, game);
        }
        return false;
    }

    @Override
    public PlatedPegasusEffect copy() {
        return new PlatedPegasusEffect(this);
    }
}