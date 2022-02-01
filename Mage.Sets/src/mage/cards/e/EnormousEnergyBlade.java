package mage.cards.e;

import mage.abilities.common.AttachedToCreatureSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Addictiveme
 */
public final class EnormousEnergyBlade extends CardImpl {

    public EnormousEnergyBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");
        
        this.subtype.add(SubType.EQUIPMENT);
        
        // Equipped creature gets +4/+0
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(4, 0)));
        
        // Whenever Enormous Energy Blade becomes attached to a creature, tap that creature.
        this.addAbility(new AttachedToCreatureSourceTriggeredAbility(new TapAttachedEffect(), false));
        
        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private EnormousEnergyBlade(final EnormousEnergyBlade card) {
        super(card);
    }

    @Override
    public EnormousEnergyBlade copy() {
        return new EnormousEnergyBlade(this);
    }
}

class TapAttachedEffect extends TapEnchantedEffect {
	
	public TapAttachedEffect() {
		super();
		this.staticText = "tap that creature";
	}
	
	public TapAttachedEffect(final TapAttachedEffect effect) {
		super(effect);
	}
	
	@Override
    public TapAttachedEffect copy() {
        return new TapAttachedEffect(this);
    }
}