
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionUntapNotMoreThanEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class WinterOrb extends CardImpl {

    public WinterOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // As long as Winter Orb is untapped, players can't untap more than one land during their untap steps.
        // Players can't untap more than one land during their untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WinterOrbEffect()));

    }

    private WinterOrb(final WinterOrb card) {
        super(card);
    }

    @Override
    public WinterOrb copy() {
        return new WinterOrb(this);
    }
}

class WinterOrbEffect extends RestrictionUntapNotMoreThanEffect {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public WinterOrbEffect() {
        super(Duration.WhileOnBattlefield, 1, filter);
        staticText = "As long as Winter Orb is untapped, players can't untap more than one land during their untap steps";
    }

    private WinterOrbEffect(final WinterOrbEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Player player, Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        return sourceObject != null && !sourceObject.isTapped();
    }

    @Override
    public WinterOrbEffect copy() {
        return new WinterOrbEffect(this);
    }

}
