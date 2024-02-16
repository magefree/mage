
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Luna Skyrise
 */
public final class ShiftingSky extends CardImpl {

    public ShiftingSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // As Shifting Sky enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Detriment)));

        // All nonland permanents are the chosen color.        
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ShiftingSkyEffect()));
    }

    private ShiftingSky(final ShiftingSky card) {
        super(card);
    }

    @Override
    public ShiftingSky copy() {
        return new ShiftingSky(this);
    }
}

class ShiftingSkyEffect extends ContinuousEffectImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("All nonland permanents");
    
    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }
    
    public ShiftingSkyEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        staticText = "All nonland permanents are the chosen color";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
            if (color == null) {
                return false;
            }
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                perm.getColor(game).setColor(color);
            }
            return true;
        }
        return false;
    }
    
    @Override
    public ShiftingSkyEffect copy() {
        return new ShiftingSkyEffect(this);
    }

    private ShiftingSkyEffect(ShiftingSkyEffect effect) {
        super(effect);
    }

}
