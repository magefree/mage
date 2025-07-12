
package mage.cards.d;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.CreaturesBecomeOtherTypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DismissIntoDream extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each creature your opponents control");
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public DismissIntoDream(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{6}{U}");

        // Each creature your opponents control is an Illusion in addition to its other types 
        // and has "When this creature becomes the target of a spell or ability, sacrifice it."
        this.addAbility(new SimpleStaticAbility(new DismissIntoDreamEffect(filter)));
    }

    private DismissIntoDream(final DismissIntoDream card) {
        super(card);
    }

    @Override
    public DismissIntoDream copy() {
        return new DismissIntoDream(this);
    }
}

class DismissIntoDreamEffect extends CreaturesBecomeOtherTypeEffect {

    DismissIntoDreamEffect(FilterPermanent filter) {
        super(filter, SubType.ILLUSION, Duration.WhileOnBattlefield);
        this.outcome = Outcome.Detriment;
        this.staticText = this.staticText + " and has \"When this creature becomes the target of a spell or ability, sacrifice it.\"";
    }

    private DismissIntoDreamEffect(final DismissIntoDreamEffect effect) {
        super(effect);
    }

    @Override
    public DismissIntoDreamEffect copy() {
        return new DismissIntoDreamEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, this.subType);
                    break;
                case AbilityAddingRemovingEffects_6:
                    permanent.addAbility(new BecomesTargetSourceTriggeredAbility(new SacrificeSourceEffect()), source.getSourceId(), game);
                    break;
            }
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return super.hasLayer(layer) || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
