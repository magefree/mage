package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.SpaceflightAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class Exogorth extends CardImpl {

    public Exogorth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.SLUG);
        this.power = new MageInt(8);
        this.toughness = new MageInt(7);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Exogorth can block only creatures with spaceflight.
        this.addAbility(new CanBlockOnlySpaceflightAbility());
    }

    private Exogorth(final Exogorth card) {
        super(card);
    }

    @Override
    public Exogorth copy() {
        return new Exogorth(this);
    }
}

class CanBlockOnlySpaceflightAbility extends SimpleStaticAbility {

    public CanBlockOnlySpaceflightAbility() {
        super(Zone.BATTLEFIELD, new CanBlockOnlySpaceflightEffect(Duration.WhileOnBattlefield));
    }

    private CanBlockOnlySpaceflightAbility(CanBlockOnlySpaceflightAbility ability) {
        super(ability);
    }

    @Override
    public CanBlockOnlySpaceflightAbility copy() {
        return new CanBlockOnlySpaceflightAbility(this);
    }
}

class CanBlockOnlySpaceflightEffect extends RestrictionEffect {

    public CanBlockOnlySpaceflightEffect(Duration duration) {
        super(duration);
        this.staticText = "{this} can block only creatures with spaceflight";
    }

    private CanBlockOnlySpaceflightEffect(final CanBlockOnlySpaceflightEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        return attacker.getAbilities().contains(SpaceflightAbility.getInstance());
    }

    @Override
    public CanBlockOnlySpaceflightEffect copy() {
        return new CanBlockOnlySpaceflightEffect(this);
    }

}
