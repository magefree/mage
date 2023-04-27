package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksAttachedTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author zeffirojoe
 */
public final class WandOfOrcus extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ZOMBIE, "Zombies");

    public WandOfOrcus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks or blocks, it and Zombies you control gain
        // deathtouch until end of turn.
        Ability deathTouchAbility = new AttacksOrBlocksAttachedTriggeredAbility(new GainAbilityAttachedEffect(
                DeathtouchAbility.getInstance(), AttachmentType.EQUIPMENT, Duration.EndOfTurn
        ).setText("it"), AttachmentType.EQUIPMENT);
        deathTouchAbility.addEffect(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn, filter
        ).concatBy("and"));
        this.addAbility(deathTouchAbility);

        // Whenever equipped creature deals combat damage to a player, create that many
        // 2/2 black Zombie creature tokens.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new CreateTokenEffect(new ZombieToken(), SavedDamageValue.MANY),
                "equipped creature",
                false));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), new TargetControlledCreaturePermanent(), false));
    }

    private WandOfOrcus(final WandOfOrcus card) {
        super(card);
    }

    @Override
    public WandOfOrcus copy() {
        return new WandOfOrcus(this);
    }
}
