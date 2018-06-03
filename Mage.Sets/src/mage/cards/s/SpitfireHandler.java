
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class SpitfireHandler extends CardImpl {

    public SpitfireHandler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Spitfire Handler can't block creatures with power greater than Spitfire Handler's power.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpitfireHandlerCantBlockEffect()));

        // {R}: Spitfire Handler gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.R)));
    }

    public SpitfireHandler(final SpitfireHandler card) {
        super(card);
    }

    @Override
    public SpitfireHandler copy() {
        return new SpitfireHandler(this);
    }
}

class SpitfireHandlerCantBlockEffect extends RestrictionEffect {

    public SpitfireHandlerCantBlockEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't block creatures with power greater than {this}'s power";
    }

    public SpitfireHandlerCantBlockEffect(final SpitfireHandlerCantBlockEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return (blocker.getPower().getValue() >= attacker.getPower().getValue());
    }

    @Override
    public SpitfireHandlerCantBlockEffect copy() {
        return new SpitfireHandlerCantBlockEffect(this);
    }
}
