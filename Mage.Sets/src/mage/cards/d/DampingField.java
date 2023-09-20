
package mage.cards.d;

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
public final class DampingField extends CardImpl {

    public DampingField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");


        // Players can't untap more than one artifact during their untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DampingFieldEffect()));
    }

    private DampingField(final DampingField card) {
        super(card);
    }

    @Override
    public DampingField copy() {
        return new DampingField(this);
    }
}

class DampingFieldEffect extends RestrictionUntapNotMoreThanEffect {

    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("an artifact");

    public DampingFieldEffect() {
        super(Duration.WhileOnBattlefield, 1, filter);
        staticText = "Players can't untap more than one artifact during their untap steps";
    }

    private DampingFieldEffect(final DampingFieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Player player, Ability source, Game game) {
        // applies to all players
        return true;
    }

    @Override
    public DampingFieldEffect copy() {
        return new DampingFieldEffect(this);
    }

}
