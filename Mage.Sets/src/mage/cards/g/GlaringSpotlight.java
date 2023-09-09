
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Gatecrash FAQ 21.01.2013
 *
 * Creatures your opponents control don't actually lose hexproof, although you
 * will ignore hexproof for purposes of choosing targets of spells and abilities
 * you control.
 *
 * Creatures that come under your control after Glaring Spotlight's last ability
 * resolves won't have hexproof but can't be blocked that turn.
 *
 * @author LevelX2
 */
public final class GlaringSpotlight extends CardImpl {

    public GlaringSpotlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Creatures your opponents control with hexproof can be the targets of spells and abilities you control as though they didn't have hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GlaringSpotlightEffect()));

        // {3}, Sacrifice Glaring Spotlight: Creatures you control gain hexproof until end of turn and can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD, new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false),
                new GenericManaCost(3));
        ability.addEffect(new CantBeBlockedAllEffect(new FilterControlledCreaturePermanent(), Duration.EndOfTurn));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private GlaringSpotlight(final GlaringSpotlight card) {
        super(card);
    }

    @Override
    public GlaringSpotlight copy() {
        return new GlaringSpotlight(this);
    }
}

class GlaringSpotlightEffect extends AsThoughEffectImpl {

    public GlaringSpotlightEffect() {
        super(AsThoughEffectType.HEXPROOF, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures your opponents control with hexproof can be the targets of spells and abilities you control as though they didn't have hexproof";
    }

    private GlaringSpotlightEffect(final GlaringSpotlightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GlaringSpotlightEffect copy() {
        return new GlaringSpotlightEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Permanent creature = game.getPermanent(sourceId);
            if (creature != null) {
                if (game.getOpponents(source.getControllerId()).contains(creature.getControllerId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
