/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.Zone;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.mana.ManaAbility;
import mage.util.Copier;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AbilitiesImpl extends ArrayList<Ability> implements Abilities, Serializable {


	@Override
	public List<String> getRules() {
		List<String> rules = new ArrayList<String>();

		for (Ability ability:this) {
			if (!(ability instanceof SpellAbility || ability instanceof PlayLandAbility))
				rules.add(ability.getRule());
		}

		return rules;
	}

	@Override
	public List<ActivatedAbility> getActivatedAbilities(Zone zone) {
		List<ActivatedAbility> zonedAbilities = new ArrayList<ActivatedAbility>();
		for (Ability ability: this) {
			if (ability instanceof ActivatedAbility && ability.getZone().match(zone)) {
				zonedAbilities.add((ActivatedAbility)ability);
			}
		}
		return zonedAbilities;
	}

	@Override
	public Abilities copy() {
		return new Copier<Abilities>().copy(this);
	}

	@Override
	public List<ManaAbility> getManaAbilities(Zone zone) {
		List<ManaAbility> abilities = new ArrayList<ManaAbility>();
		for (Ability ability: this) {
			if (ability instanceof ManaAbility && ability.getZone().match(zone)) {
				abilities.add((ManaAbility)ability);
			}
		}
		return abilities;
	}

	@Override
	public List<EvasionAbility> getEvasionAbilities() {
		List<EvasionAbility> abilities = new ArrayList<EvasionAbility>();
		for (Ability ability: this) {
			if (ability instanceof EvasionAbility) {
				abilities.add((EvasionAbility)ability);
			}
		}
		return abilities;
	}

	@Override
	public List<StaticAbility> getStaticAbilities(Zone zone) {
		List<StaticAbility> zonedAbilities = new ArrayList<StaticAbility>();
		for (Ability ability: this) {
			if (ability instanceof StaticAbility && ability.getZone().match(zone)) {
				zonedAbilities.add((StaticAbility)ability);
			}
		}
		return zonedAbilities;
	}

	@Override
	public List<TriggeredAbility> getTriggeredAbilities(Zone zone) {
		List<TriggeredAbility> zonedAbilities = new ArrayList<TriggeredAbility>();
		for (Ability ability: this) {
			if (ability instanceof TriggeredAbility && ability.getZone().match(zone)) {
				zonedAbilities.add((TriggeredAbility)ability);
			}
		}
		return zonedAbilities;
	}

	@Override
	public List<ProtectionAbility> getProtectionAbilities() {
		List<ProtectionAbility> abilities = new ArrayList<ProtectionAbility>();
		for (Ability ability: this) {
			if (ability instanceof ProtectionAbility) {
				abilities.add((ProtectionAbility)ability);
			}
		}
		return abilities;
	}

	@Override
	public List<KickerAbility> getKickerAbilities() {
		List<KickerAbility> abilities = new ArrayList<KickerAbility>();
		for (Ability ability: this) {
			if (ability instanceof KickerAbility) {
				abilities.add((KickerAbility)ability);
			}
		}
		return abilities;
	}

	@Override
	public void setControllerId(UUID controllerId) {
		for (Ability ability: this) {
			ability.setControllerId(controllerId);
		}
	}

	@Override
	public void setSourceId(UUID sourceId) {
		for (Ability ability: this) {
			ability.setSourceId(sourceId);
		}
	}

	@Override
	public boolean containsAll(Abilities abilities) {
		if (this.size() < abilities.size())
			return false;
		for (Ability ability: abilities) {
			boolean found = false;
			for (Ability test: this) {
				if (ability.getRule().equals(test.getRule())) {
					found = true;
					break;
				}
				if (!found)
					return false;
			}
		}
		return true;
	}

	@Override
	public boolean containsKey(UUID abilityId) {
		for (Ability ability: this) {
			if (ability.getId().equals(abilityId))
				return true;
		}
		return false;
	}

	@Override
	public Ability get(UUID abilityId) {
		for (Ability ability: this) {
			if (ability.getId().equals(abilityId))
				return ability;
		}
		return null;
	}

}
