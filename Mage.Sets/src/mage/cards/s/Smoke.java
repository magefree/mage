
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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author KholdFuzion

 */
public final class Smoke extends CardImpl {

    public Smoke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}{R}");


        // Players can't untap more than one creature during their untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SmokeEffect()));
    }

    private Smoke(final Smoke card) {
        super(card);
    }

    @Override
    public Smoke copy() {
        return new Smoke(this);
    }
}

class SmokeEffect extends RestrictionUntapNotMoreThanEffect {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent();

    public SmokeEffect() {
        super(Duration.WhileOnBattlefield, 1, filter);
        staticText = "Players can't untap more than one creature during their untap steps";
    }

    private SmokeEffect(final SmokeEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Player player, Ability source, Game game) {
        // applied to all players
        return true;
    }

    @Override
    public SmokeEffect copy() {
        return new SmokeEffect(this);
    }

}
