
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class DismissIntoDream extends CardImpl {

    public DismissIntoDream(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{6}{U}");


        // Each creature your opponents control is an Illusion in addition to its other types 
        // and has "When this creature becomes the target of a spell or ability, sacrifice it."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DismissIntoDreamEffect()));
    }

    public DismissIntoDream(final DismissIntoDream card) {
        super(card);
    }

    @Override
    public DismissIntoDream copy() {
        return new DismissIntoDream(this);
    }
}

class DismissIntoDreamEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    DismissIntoDreamEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Each creature your opponents control is an Illusion in addition to its other types and has \"When this creature becomes the target of a spell or ability, sacrifice it.\"";
    }

    DismissIntoDreamEffect(final DismissIntoDreamEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public DismissIntoDreamEffect copy() {
        return new DismissIntoDreamEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent object: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    object.addAbility(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect()), source.getSourceId(), game);
                    break;
                case TypeChangingEffects_4:
                    if (!object.hasSubtype(SubType.ILLUSION, game)) {
                        object.getSubtype(game).add(SubType.ILLUSION);
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
