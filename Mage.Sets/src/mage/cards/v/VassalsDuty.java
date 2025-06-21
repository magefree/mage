package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VassalsDuty extends CardImpl {

    public VassalsDuty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // {1}: The next 1 damage that would be dealt to target legendary creature you control this turn is dealt to you instead.
        Ability ability = new SimpleActivatedAbility(new VassalsDutyPreventDamageTargetEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY));
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

    VassalsDutyPreventDamageTargetEffect() {
        super(Duration.EndOfTurn, 1, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next 1 damage that would be dealt to target legendary creature you control this turn is dealt to you instead";
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
        if (!event.getTargetId().equals(getTargetPointer().getFirst(game, source))) {
            return false;
        }
        TargetPlayer target = new TargetPlayer();
        target.add(source.getControllerId(), game);
        redirectTarget = target;
        return true;
    }

}
