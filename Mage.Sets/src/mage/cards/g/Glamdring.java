package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;

/**
 *
 * @author anonymous
 */
public final class Glamdring extends CardImpl {

    public Glamdring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has first strike and gets +1/+0 for each instant and sorcery card in your graveyard.
        Effect firstStrike = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText("Equipped creature has first strike");
        Effect boost = new BoostEquippedEffect(
                new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY), StaticValue.get(0)
        ).setText(" and gets +1/+0 for each instant and sorcery card in your graveyard.");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, firstStrike);
        ability.addEffect(boost);
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, you may cast an instant or sorcery spell from your hand with mana value less than or equal to that damage without paying its mana cost.

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private Glamdring(final Glamdring card) {
        super(card);
    }

    @Override
    public Glamdring copy() {
        return new Glamdring(this);
    }
}
