package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public final class GravePeril extends CardImpl {

    public GravePeril(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");

        // When a nonblack creature enters the battlefield, sacrifice Grave Peril. If you do, destroy that creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new GravePerilEffect(), StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK, false, SetTargetPointer.PERMANENT, null).setTriggerPhrase("When a nonblack creature enters the battlefield, "));
    }

    private GravePeril(final GravePeril card) {
        super(card);
    }

    @Override
    public GravePeril copy() {
        return new GravePeril(this);
    }
}

class GravePerilEffect extends OneShotEffect {

    GravePerilEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "sacrifice Grave Peril. If you do, destroy that creature";
    }

    GravePerilEffect(final GravePerilEffect effect) {
        super(effect);
    }

    @Override
    public GravePerilEffect copy() {
        return new GravePerilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            if (permanent.sacrifice(source, game)) {
                Effect effect = new DestroyTargetEffect();
                effect.setTargetPointer(this.getTargetPointer());
                return effect.apply(game, source);
            }
        }
        return false;
    }
}
