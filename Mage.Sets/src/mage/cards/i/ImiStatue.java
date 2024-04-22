
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionUntapNotMoreThanEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ImiStatue extends CardImpl {

    public ImiStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Players can't untap more than one artifact during their untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ImiStatueEffect()));

    }

    private ImiStatue(final ImiStatue card) {
        super(card);
    }

    @Override
    public ImiStatue copy() {
        return new ImiStatue(this);
    }
}

class ImiStatueEffect extends RestrictionUntapNotMoreThanEffect {

    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("an artifact");

    public ImiStatueEffect() {
        super(Duration.WhileOnBattlefield, 1, filter);
        staticText = "Players can't untap more than one artifact during their untap steps";
    }

    private ImiStatueEffect(final ImiStatueEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Player player, Ability source, Game game) {
        // applied to all players
        return true;
    }

    @Override
    public ImiStatueEffect copy() {
        return new ImiStatueEffect(this);
    }

}
