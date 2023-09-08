package mage.cards.n;

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
 * @author LevelX2
 */
public final class NullRod extends CardImpl {

    public NullRod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Activated abilities of artifacts can't be activated.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NullRodCantActivateEffect()));
    }

    private NullRod(final NullRod card) {
        super(card);
    }

    @Override
    public NullRod copy() {
        return new NullRod(this);
    }
}

class NullRodCantActivateEffect extends RestrictionEffect {

    public NullRodCantActivateEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Activated abilities of artifacts can't be activated";
    }

    private NullRodCantActivateEffect(final NullRodCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isArtifact(game);
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public NullRodCantActivateEffect copy() {
        return new NullRodCantActivateEffect(this);
    }

}