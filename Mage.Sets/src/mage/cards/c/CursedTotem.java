package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class CursedTotem extends CardImpl {

    public CursedTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Activated abilities of creatures can't be activated.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CursedTotemCantActivateEffect()));
    }

    private CursedTotem(final CursedTotem card) {
        super(card);
    }

    @Override
    public CursedTotem copy() {
        return new CursedTotem(this);
    }
}

class CursedTotemCantActivateEffect extends RestrictionEffect {

    public CursedTotemCantActivateEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Activated abilities of creatures can't be activated";
    }

    private CursedTotemCantActivateEffect(final CursedTotemCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isCreature(game);
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public CursedTotemCantActivateEffect copy() {
        return new CursedTotemCantActivateEffect(this);
    }

}