package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.GraftAbility;
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
 * @author JotaPeRL
 */
public final class VigeanHydropon extends CardImpl {

    public VigeanHydropon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Graft 5
        this.addAbility(new GraftAbility(this, 5));

        // Vigean Hydropon can't attack or block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VigeanHydroponEffect()));
    }

    private VigeanHydropon(final VigeanHydropon card) {
        super(card);
    }

    @Override
    public VigeanHydropon copy() {
        return new VigeanHydropon(this);
    }
}

class VigeanHydroponEffect extends RestrictionEffect {

    public VigeanHydroponEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block";
    }

    private VigeanHydroponEffect(final VigeanHydroponEffect effect) {
        super(effect);
    }

    @Override
    public VigeanHydroponEffect copy() {
        return new VigeanHydroponEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }
}