package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LupinePrototype extends CardImpl {

    public LupinePrototype(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Lupine Prototype can't attack or block unless a player has no cards in hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LupinePrototypeEffect()));
    }

    public LupinePrototype(final LupinePrototype card) {
        super(card);
    }

    @Override
    public LupinePrototype copy() {
        return new LupinePrototype(this);
    }
}

class LupinePrototypeEffect extends RestrictionEffect {

    public LupinePrototypeEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless a player has no cards in hand";
    }

    public LupinePrototypeEffect(final LupinePrototypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            for (Player player : game.getPlayers().values()) {
                if (player != null && player.getHand().isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        // don't apply for all other creatures!
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public LupinePrototypeEffect copy() {
        return new LupinePrototypeEffect(this);
    }
}
