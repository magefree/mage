package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class ThorinMountainKing extends CardImpl {

    public ThorinMountainKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Thorin enters, attach any number of target Equipment you control to target creature you control. When one or more Equipment become attached to that creature this way, that creature deals damage equal to its power to up to one target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ThorinMountainKingEffect());
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ThorinMountainKing(final ThorinMountainKing card) {
        super(card);
    }

    @Override
    public ThorinMountainKing copy() {
        return new ThorinMountainKing(this);
    }
}

class ThorinMountainKingEffect extends OneShotEffect {

    ThorinMountainKingEffect() {
        super(Outcome.Benefit);
        staticText = "attach any number of target Equipment you control to target creature you control. "
                + "When one or more Equipment become attached to that creature this way, "
                + "that creature deals damage equal to its power to up to one target creature";
    }

    private ThorinMountainKingEffect(final ThorinMountainKingEffect effect) {
        super(effect);
    }

    @Override
    public ThorinMountainKingEffect copy() {
        return new ThorinMountainKingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() < 2) {
            return false;
        }

        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature == null) {
            return false;
        }

        int attached = 0;
        for (UUID equipmentId : source.getTargets().get(0).getTargets()) {
            Permanent equipment = game.getPermanent(equipmentId);
            if (equipment != null && equipment.hasSubtype(SubType.EQUIPMENT, game)
                    && creature.addAttachment(equipment.getId(), source, game)) {
                attached++;
            }
        }

        if (attached > 0) {
            ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ThorinMountainKingDamageEffect(creature.getId()), false,
                "that creature deals damage equal to its power to up to one target creature"
            );
            ability.addTarget(new TargetCreaturePermanent(0, 1));
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        return true;
    }
}

class ThorinMountainKingDamageEffect extends OneShotEffect {

    private final UUID creatureId;

    ThorinMountainKingDamageEffect(UUID creatureId) {
        super(Outcome.Damage);
        this.creatureId = creatureId;
    }

    private ThorinMountainKingDamageEffect(final ThorinMountainKingDamageEffect effect) {
        super(effect);
        this.creatureId = effect.creatureId;
    }

    @Override
    public ThorinMountainKingDamageEffect copy() {
        return new ThorinMountainKingDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(creatureId);
        if (creature == null) {
            return false;
        }

        int damage = Math.max(0, creature.getPower().getValue());
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent target = game.getPermanent(targetId);
            if (target != null) {
                target.damage(damage, creature.getId(), source, game, false, true);
            }
        }
        return true;
    }
}
