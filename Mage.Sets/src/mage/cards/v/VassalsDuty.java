
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VassalsDuty extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("legendary creature you control");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public VassalsDuty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // {1}: The next 1 damage that would be dealt to target legendary creature you control this turn is dealt to you instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VassalsDutyPreventDamageTargetEffect(Duration.EndOfTurn, 1), new GenericManaCost(1));
        ability.addTarget(new TargetControlledCreaturePermanent(1, 1, filter, false));
        this.addAbility(ability);
    }

    private VassalsDuty(final VassalsDuty card) {
        super(card);
    }

    @Override
    public VassalsDuty copy() {
        return new VassalsDuty(this);
    }
}

class VassalsDutyPreventDamageTargetEffect extends RedirectionEffect {

    public VassalsDutyPreventDamageTargetEffect(Duration duration, int amount) {
        super(duration, amount, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next " + amount + " damage that would be dealt to target legendary creature you control this turn is dealt to you instead";
    }

    private VassalsDutyPreventDamageTargetEffect(final VassalsDutyPreventDamageTargetEffect effect) {
        super(effect);
    }

    @Override
    public VassalsDutyPreventDamageTargetEffect copy() {
        return new VassalsDutyPreventDamageTargetEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(getTargetPointer().getFirst(game, source))) {
            TargetPlayer target = new TargetPlayer();
            target.add(source.getControllerId(), game);
            redirectTarget = target;
            return true;
        }
        return false;
    }

}
