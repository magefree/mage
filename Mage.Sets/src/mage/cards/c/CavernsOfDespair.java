package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CavernsOfDespair extends CardImpl {

    public CavernsOfDespair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        this.supertype.add(SuperType.WORLD);

        // No more than two creatures can attack each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CavernsOfDespairAttackRestrictionEffect()));

        // No more than two creatures can block each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CavernsOfDespairBlockRestrictionEffect()));
    }

    private CavernsOfDespair(final CavernsOfDespair card) {
        super(card);
    }

    @Override
    public CavernsOfDespair copy() {
        return new CavernsOfDespair(this);
    }
}

class CavernsOfDespairAttackRestrictionEffect extends RestrictionEffect {

    public CavernsOfDespairAttackRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "No more than two creatures can attack each combat";
    }

    public CavernsOfDespairAttackRestrictionEffect(final CavernsOfDespairAttackRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public CavernsOfDespairAttackRestrictionEffect copy() {
        return new CavernsOfDespairAttackRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        return game.getCombat().getAttackers().size() < 2;
    }
}

class CavernsOfDespairBlockRestrictionEffect extends RestrictionEffect {

    public CavernsOfDespairBlockRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "No more than two creatures can block each combat";
    }

    public CavernsOfDespairBlockRestrictionEffect(final CavernsOfDespairBlockRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public CavernsOfDespairBlockRestrictionEffect copy() {
        return new CavernsOfDespairBlockRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return game.getCombat().getBlockers().size() < 2;
    }
}
