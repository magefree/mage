
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
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
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DismissIntoDreamEffect(filter)));
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
        super.apply(layer, sublayer, source, game);

        if (layer == Layer.AbilityAddingRemovingEffects_6) {
            for (Permanent object: game.getBattlefield().getActivePermanents(this.filter, source.getControllerId(), game)) {
                object.addAbility(new SourceBecomesTargetTriggeredAbility(new SacrificeSourceEffect()), source.getSourceId(), game);
            }
        }

        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return super.hasLayer(layer) || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
