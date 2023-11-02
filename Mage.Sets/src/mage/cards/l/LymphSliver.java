
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Luna Skyrise
 */
public final class LymphSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SLIVER, "All Sliver creatures");

    public LymphSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // All Sliver creatures have absorb 1.
        Ability absorb = new SimpleStaticAbility(Zone.BATTLEFIELD, new SliverAbsorbEffect());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(absorb,
                Duration.WhileOnBattlefield, filter, "All Sliver creatures have absorb 1. <i>(If a source would deal damage to a Sliver, prevent 1 of that damage.)</i>")));
    }

    private LymphSliver(final LymphSliver card) {
        super(card);
    }

    @Override
    public LymphSliver copy() {
        return new LymphSliver(this);
    }
}

class SliverAbsorbEffect extends PreventionEffectImpl {

    public SliverAbsorbEffect() {
        super(Duration.WhileOnBattlefield, 1, false, false);
        this.staticText = "Absorb 1 <i>(If a source would deal damage to this creature, prevent 1 of that damage.</i>)";
    }

    private SliverAbsorbEffect(final SliverAbsorbEffect effect) {
        super(effect);
    }

    @Override
    public SliverAbsorbEffect copy() {
        return new SliverAbsorbEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId()) && super.applies(event, source, game);
    }

}
