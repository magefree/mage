package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class Thunderstaff extends CardImpl {

    public Thunderstaff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // As long as Thunderstaff is untapped, if a creature would deal combat damage to you, prevent 1 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ThunderstaffPreventionEffect()));
        // {2}, {tap}: Attacking creatures get +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(1,0,Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private Thunderstaff(final Thunderstaff card) {
        super(card);
    }

    @Override
    public Thunderstaff copy() {
        return new Thunderstaff(this);
    }
}

class ThunderstaffPreventionEffect extends PreventionEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public ThunderstaffPreventionEffect() {
        super(Duration.WhileOnBattlefield, 1, true, false);
        staticText = "As long as {this} is untapped, if a creature would deal combat damage to you, prevent 1 of that damage";
    }

    public ThunderstaffPreventionEffect(final ThunderstaffPreventionEffect effect) {
        super(effect);
    }

    @Override
    public ThunderstaffPreventionEffect copy() {
        return new ThunderstaffPreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId())){
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null && !sourcePermanent.isTapped()) {
                    Permanent damageSource = game.getPermanent(event.getSourceId());
                    if (damageSource != null && filter.match(damageSource, game)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

}
