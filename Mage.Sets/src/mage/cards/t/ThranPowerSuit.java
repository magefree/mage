package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThranPowerSuit extends CardImpl {

    public ThranPowerSuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each Aura and Equipment attached to it and has ward {2}.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(
                ThranPowerSuitValue.instance, ThranPowerSuitValue.instance
        ));
        ability.addEffect(new GainAbilityAttachedEffect(new WardAbility(
                new GenericManaCost(2), false
        ), AttachmentType.EQUIPMENT).setText("and has ward {2}. <i>(Whenever equipped creature becomes the target of a spell or ability an opponent controls, counter it unless that player pays {2}.)</i>"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private ThranPowerSuit(final ThranPowerSuit card) {
        super(card);
    }

    @Override
    public ThranPowerSuit copy() {
        return new ThranPowerSuit(this);
    }
}

enum ThranPowerSuitValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = sourceAbility.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return 0;
        }
        Permanent permanent = game.getPermanent(sourcePermanent.getAttachedTo());
        return permanent == null ? 0 : permanent
                .getAttachments()
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .map(p -> p.hasSubtype(SubType.EQUIPMENT, game) || p.hasSubtype(SubType.AURA, game))
                .mapToInt(b -> b ? 1 : 0)
                .sum();
    }

    @Override
    public ThranPowerSuitValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "Aura and Equipment attached to it";
    }

    @Override
    public String toString() {
        return "1";
    }
}
