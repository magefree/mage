package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HolyFrazzleCannon extends CardImpl {

    public HolyFrazzleCannon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.subtype.add(SubType.EQUIPMENT);
        this.color.setWhite(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // Whenever equipped creature attacks, put a +1/+1 counter on that creature and each other creature you control that shares a creature type with it.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new HolyFrazzleCannonEffect(), AttachmentType.EQUIPMENT,
                false, SetTargetPointer.PERMANENT
        ));

        // Equip {1}
        this.addAbility(new EquipAbility(1, false));
    }

    private HolyFrazzleCannon(final HolyFrazzleCannon card) {
        super(card);
    }

    @Override
    public HolyFrazzleCannon copy() {
        return new HolyFrazzleCannon(this);
    }
}

class HolyFrazzleCannonEffect extends OneShotEffect {

    HolyFrazzleCannonEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on that creature and " +
                "each other creature you control that shares a creature type with it";
    }

    private HolyFrazzleCannonEffect(final HolyFrazzleCannonEffect effect) {
        super(effect);
    }

    @Override
    public HolyFrazzleCannonEffect copy() {
        return new HolyFrazzleCannonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game
        )) {
            if (creature.equals(permanent) || permanent.shareCreatureTypes(game, creature)) {
                creature.addCounters(CounterType.P1P1.createInstance(), source, game);
            }
        }
        return true;
    }
}
