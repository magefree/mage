package mage.sets.odyssey

import mage.card.CardImpl;
import


import java.util.UUID

public class AvenShrine extends CardImpl<AvenShrine> {

	public AvenShrine(ownerID UUID){
		super(ownerID, 9, "Aven Shrine", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}"); 
		this.ExpansionSetCode = "ODY";
		
		// Whenever a player casts a spell, that player gains X life, 
		// where X is the number of cards in all graveyards with the same name as that spell.
		
		this.addAbility = new(AvenShrineAbility());
		
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
			super(Zone.BATTLEFILED, new GainLifeEffect(new CardsInAllGraveyardCount(filter)), true);
		}
		
		public AvenShrineAbility(final AvenShrineAbility ability) {
        super(ability);
    }

    @Override
    public AvenShrineAbility copy() {
        return new AngelsFeatherAbility(this);
    }
	
	// I don't know if you can use the filter before it is declared nor if the code as written will correctly count
	// all of the cards with the same name as the spell just cast that are in all graveyards.
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.GetType == EventType.SPELL_CAST) {
			String cardName = game.getStack().getSpell(event.getName());
			FilterCard filter = FilterCard(cardName);
				if (CardsInAllGraveyardCount(filter) != 0) {
					return true;
				}
		}
	return false;
	}
