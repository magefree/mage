package mage.sets.odyssey

import mage.card.CardImpl;
import mage.constants.Rarity;
import mage.constants.CardType;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.GainLifeTriggeredEffect;




import java.util.UUID

public class AvenShrine extends CardImpl<AvenShrine> {

	public AvenShrine(ownerID UUID){
		super(ownerID, 9, "Aven Shrine", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}"); 
		this.ExpansionSetCode = "ODY";
		this.color.setWhite(true);
		
		// Whenever a player casts a spell, that player gains X life, 
		// where X is the number of cards in all graveyards with the same name as that spell.
		
		this.addAbility = new AvenShrineAbility() ;
		
	}
	
		public AvenShrine(final AngelShrine card) {
			super(card);
		}
	
	@Override
		public AvenShrine copy() {
			return new AvenShrine(this);
		}
		
	}
	
public class AvenShrineAbility extends TriggeredAbilityImpl<AvenShrineAbility> {

		
		public AvenShrineAbility(){
			super(Zone.BATTLEFIELD, new GainLifeTargetEffect(new CardsInAllGraveyardCount(filter)), false);
		}
		
		public AvenShrineAbility(final AvenShrineAbility ability) {
        super(ability);
    }

    @Override
    public AvenShrineAbility copy() {
        return new AngelsFeatherAbility(this);
    }
	

	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.GetType == EventType.SPELL_CAST) {
			Spell spell = game.getStack().getSpell(event.getTargetID())
			String cardName = game.getStack().getSpell(event.getName());
			FilterCard filter = FilterCard(cardName);
			if (CardsInAllGraveyardCount(filter) != 0) {
				if (spell != null) {
                			for (Effect effect : this.getEffects()) {
                    			effect.setTargetPointer(new FixedTarget(spell.getControllerId()));
                				}
                			return true;
				}
			}	
			return true;
		}
	return false;
	}
}


