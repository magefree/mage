package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.AfflictAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;

/**
 *
 * @author ciaccona007
 */
public final class DaggerOfTheWorthy extends CardImpl {

    public DaggerOfTheWorthy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0 and has afflict 1. (Whenever it becomes blocked, defending player loses 1 life.)
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2,0));
        ability.addEffect(new GainAbilityAttachedEffect(new AfflictAbility(1), AttachmentType.EQUIPMENT)
                .setText("and has afflict 1"));
        this.addAbility(ability);
        
        // Equip {2} ({2}: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
        
    }

    private DaggerOfTheWorthy(final DaggerOfTheWorthy card) {
        super(card);
    }

    @Override
    public DaggerOfTheWorthy copy() {
        return new DaggerOfTheWorthy(this);
    }
}
