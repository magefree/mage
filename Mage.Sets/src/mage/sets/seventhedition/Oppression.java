package mage.sets.seventh

import mage.abilities.Ability;
import mage.abilities.common.SimpleTriggeredAbility;
import mage.card.CardImpl;
import mage.constants.Rarity;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.abilities.effects.common.DiscardControllerEffect;

import java.util.UUID

public class Oppression extends CardImpl<Oppression> {

	public Oppression(ownerID UUID) {
		super(ownerID, 152, "Oppression", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");
		this.ExpansionSetCode = "7ED"
		Ability ability = SimpleTriggeredAbility(Zone.BATTLEFIELD, eventType.CAST_SPELL, new Effect DiscardControllerEffect(1), false);
		this.addAbility(ability);
		}
		
	public Oppression(final Oppression card)
		super(card);
		}
		
	@Override
		public Oppression copy() {
			return new Oppression(this);
		}
		
	}
