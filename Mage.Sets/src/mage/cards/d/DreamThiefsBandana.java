package mage.cards.d;

import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.effects.common.ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DreamThiefsBandana extends CardImpl {

    public DreamThiefsBandana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature deals combat damage to a player, look at the top card of their library, then exile it face down. For as long as it remains exiled, you may play it, and mana of any type can be spent to cast that spell.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(false, CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE)
                        .setText("look at the top card of their library, then exile it face down. "
                                + "For as long as it remains exiled, you may play it, and mana of any type can be spent to cast that spell"),
                "equipped creature", false, true
        ));

        // Equip {1}
        this.addAbility(new EquipAbility(1, false));
    }

    private DreamThiefsBandana(final DreamThiefsBandana card) {
        super(card);
    }

    @Override
    public DreamThiefsBandana copy() {
        return new DreamThiefsBandana(this);
    }
}
