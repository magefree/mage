
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionUntapNotMoreThanEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class StaticOrb extends CardImpl {

    public StaticOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // As long as Static Orb is untapped, players can't untap more than two permanents during their untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new StaticOrbEffect()));
    }

    private StaticOrb(final StaticOrb card) {
        super(card);
    }

    @Override
    public StaticOrb copy() {
        return new StaticOrb(this);
    }
}


class StaticOrbEffect extends RestrictionUntapNotMoreThanEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    public StaticOrbEffect() {
        super(Duration.WhileOnBattlefield, 2, filter);
        staticText = "As long as Static Orb is untapped, players can't untap more than two permanents during their untap steps";
    }

    private StaticOrbEffect(final StaticOrbEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Player player, Ability source, Game game) {
        // applied to all players
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (!permanent.isTapped()) {
            return true;
        }
        return false;
    }

    @Override
    public StaticOrbEffect copy() {
        return new StaticOrbEffect(this);
    }

}
