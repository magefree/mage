
package mage.cards.e;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author cbt33, Loki (TakenoSamuraiGeneral), North (Coat of Arms)
 */
public final class EarnestFellowship extends CardImpl {

    public EarnestFellowship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // Each creature has protection from its colors.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EarnestFellowshipEffect()));
    }

    private EarnestFellowship(final EarnestFellowship card) {
        super(card);
    }

    @Override
    public EarnestFellowship copy() {
        return new EarnestFellowship(this);
    }
}

class EarnestFellowshipEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
   

    public EarnestFellowshipEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each creature has protection from its colors";
    }

    private EarnestFellowshipEffect(final EarnestFellowshipEffect effect) {
        super(effect);
    }

    @Override
    public EarnestFellowshipEffect copy() {
        return new EarnestFellowshipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (permanent.getColor(game).hasColor()) {
                List<ColorPredicate> colorPredicates = new ArrayList<>();
                for (ObjectColor color : permanent.getColor(game).getColors()) {
                    colorPredicates.add(new ColorPredicate(color));
                }
                FilterCard filterColors = new FilterCard("its colors");
                filterColors.add(Predicates.or(colorPredicates));
                Ability ability = new ProtectionAbility(filterColors);
                permanent.addAbility(ability, source.getSourceId(), game);
            }
       }
        return true;
    }

}
