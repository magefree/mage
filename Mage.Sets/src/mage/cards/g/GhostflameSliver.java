
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author anonymous
 */
public final class GhostflameSliver extends CardImpl {

    public GhostflameSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{R}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Slivers are colorless.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GhostflameSliverEffect()));
    }

    private GhostflameSliver(final GhostflameSliver card) {
        super(card);
    }

    @Override
    public GhostflameSliver copy() {
        return new GhostflameSliver(this);
    }
}

class GhostflameSliverEffect extends ContinuousEffectImpl {
    
    private static final FilterPermanent filter = new FilterPermanent(SubType.SLIVER, "All Slivers");

    public GhostflameSliverEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        staticText = "All Slivers are colorless";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (filter.match(perm, game)) {
                perm.getColor(game).setColor(ObjectColor.COLORLESS);
            }
        }
        return true;
    }

    @Override
    public GhostflameSliverEffect copy() {
        return new GhostflameSliverEffect(this);
    }

    private GhostflameSliverEffect(final GhostflameSliverEffect effect) {
        super(effect);
    }
}
