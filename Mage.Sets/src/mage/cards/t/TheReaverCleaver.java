package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TheReaverCleaver extends CardImpl {

    public TheReaverCleaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has trample and “Whenever this creature deals
        // combat damage to a player or planeswalker, create that many Treasure tokens.”
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(),
                AttachmentType.EQUIPMENT,
                Duration.WhileOnBattlefield,
                "and has trample"
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(
                        new CreateTokenEffect(new TreasureToken(), SavedDamageValue.MANY), false),
                AttachmentType.EQUIPMENT,
                Duration.WhileOnBattlefield,
                " and \"Whenever this creature deals combat damage to a player or planeswalker, create that many Treasure tokens.\""
        ));

        this.addAbility(ability);

        // Equip 3
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), new TargetControlledCreaturePermanent(), false));
    }

    private TheReaverCleaver(final TheReaverCleaver card) {
        super(card);
    }

    @Override
    public TheReaverCleaver copy() {
        return new TheReaverCleaver(this);
    }
}
