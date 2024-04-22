
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class AlAbarasCarpet extends CardImpl {

    public AlAbarasCarpet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {5}, {T}: Prevent all damage that would be dealt to you this turn by attacking creatures without flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AlAbarasCarpetEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private AlAbarasCarpet(final AlAbarasCarpet card) {
        super(card);
    }

    @Override
    public AlAbarasCarpet copy() {
        return new AlAbarasCarpet(this);
    }
}

class AlAbarasCarpetEffect extends PreventionEffectImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature();
    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    AlAbarasCarpetEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to you this turn by attacking creatures without flying";
    }

    private AlAbarasCarpetEffect(final AlAbarasCarpetEffect effect) {
        super(effect);
    }

    @Override
    public AlAbarasCarpetEffect copy() {
        return new AlAbarasCarpetEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamagePlayerEvent && event.getAmount() > 0) {
            DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
            if (event.getTargetId().equals(source.getControllerId())) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
                if (filter.match(permanent, game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
