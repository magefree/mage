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

import mage.Constants.Zone;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.mana.ManaAbility;
import mage.game.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AbilitiesImpl<T extends Ability> extends ArrayList<T> implements Abilities<T> {

	public AbilitiesImpl() {}

    public AbilitiesImpl(T... abilities) {
        for (T ability : abilities) {
            add(ability);
        }
    }
	
	public AbilitiesImpl(final AbilitiesImpl<T> abilities) {
		for (T ability: abilities) {
			this.add((T)ability.copy());
		}
	}

	@Override
	public AbilitiesImpl<T> copy() {
		return new AbilitiesImpl<T>(this);
	}

	@Override
	public List<String> getRules(String source) {
		List<String> rules = new ArrayList<String>();

		for (T ability:this) {
			if (!(ability instanceof SpellAbility || ability instanceof PlayLandAbility))
				rules.add(ability.getRule());
		}

		return rules;
	}

	@Override
	public Abilities<ActivatedAbility> getActivatedAbilities(Zone zone) {
		Abilities<ActivatedAbility> zonedAbilities = new AbilitiesImpl<ActivatedAbility>();
		for (T ability: this) {
			if (ability instanceof ActivatedAbility && ability.getZone().match(zone)) {
				zonedAbilities.add((ActivatedAbility)ability);
			}
		}
		return zonedAbilities;
	}

	@Override
	public Abilities<ManaAbility> getManaAbilities(Zone zone) {
		Abilities<ManaAbility> abilities = new AbilitiesImpl<ManaAbility>();
		for (T ability: this) {
			if (ability instanceof ManaAbility && ability.getZone().match(zone)) {
				abilities.add((ManaAbility)ability);
			}
		}
		return abilities;
	}

	@Override
	public Abilities<ManaAbility> getAvailableManaAbilities(Zone zone, Game game) {
		Abilities<ManaAbility> abilities = new AbilitiesImpl<ManaAbility>();
		for (T ability: this) {
			if (ability instanceof ManaAbility && ability.getZone().match(zone)) {
                if ((((ManaAbility)ability).canActivate(ability.getControllerId(), game)))
                    abilities.add((ManaAbility)ability);
			}
		}
		return abilities;
	}

    @Override
	public Abilities<EvasionAbility> getEvasionAbilities() {
		Abilities<EvasionAbility> abilities = new AbilitiesImpl<EvasionAbility>();
		for (T ability: this) {
			if (ability instanceof EvasionAbility) {
				abilities.add((EvasionAbility)ability);
			}
		}
		return abilities;
	}

	@Override
	public Abilities<StaticAbility> getStaticAbilities(Zone zone) {
		Abilities<StaticAbility> zonedAbilities = new AbilitiesImpl<StaticAbility>();
		for (T ability: this) {
			if (ability instanceof StaticAbility && ability.getZone().match(zone)) {
				zonedAbilities.add((StaticAbility)ability);
			}
		}
		return zonedAbilities;
	}

	@Override
	public Abilities<TriggeredAbility> getTriggeredAbilities(Zone zone) {
		Abilities<TriggeredAbility> zonedAbilities = new AbilitiesImpl<TriggeredAbility>();
		for (T ability: this) {
			if (ability instanceof TriggeredAbility && ability.getZone().match(zone)) {
				zonedAbilities.add((TriggeredAbility)ability);
			}
			else if (ability instanceof ZoneChangeTriggeredAbility) {
				ZoneChangeTriggeredAbility zcAbility = (ZoneChangeTriggeredAbility)ability;
				if (zcAbility.getToZone() != null && zcAbility.getToZone().match(zone)) {
					zonedAbilities.add((ZoneChangeTriggeredAbility)ability);
				}
			}
		}
		return zonedAbilities;
	}
    
    @Override
	public Abilities<ProtectionAbility> getProtectionAbilities() {
		Abilities<ProtectionAbility> abilities = new AbilitiesImpl<ProtectionAbility>();
		for (T ability: this) {
			if (ability instanceof ProtectionAbility) {
				abilities.add((ProtectionAbility)ability);
			}
		}
		return abilities;
	}

	@Override
	public Abilities<KickerAbility> getKickerAbilities() {
		Abilities<KickerAbility> abilities = new AbilitiesImpl<KickerAbility>();
		for (T ability: this) {
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
	public void newId() {
		for (Ability ability: this) {
			ability.newId();
		}
	}

	@Override
	public void newOriginalId() {
		for (Ability ability: this) {
			ability.newOriginalId();
		}
	}

    @Override
	public boolean contains(T ability) {
		for (T test: this) {
			if (ability.getId().equals(test.getId()) || ability.getRule().equals(test.getRule())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Abilities<T> abilities) {
		if (this.size() < abilities.size())
			return false;
		for (T ability: abilities) {
			if (!contains(ability))
				return false;
		}
		return true;
	}

	@Override
	public boolean containsKey(UUID abilityId) {
		for (T ability: this) {
			if (ability.getId().equals(abilityId))
				return true;
		}
		return false;
	}

	@Override
	public T get(UUID abilityId) {
		for (T ability: this) {
			if (ability.getId().equals(abilityId))
				return ability;
		}
		return null;
	}

	@Override
	public int getOutcomeTotal() {
		int total = 0;
		for (T ability: this) {
			total += ability.getEffects().getOutcomeTotal();
		}
		return total;
	}

    @Override
    public String getValue() {
        List<String> abilities = new ArrayList<String>();
        for (T ability: this) {
            abilities.add(ability.toString());
        }
        Collections.sort(abilities);
        StringBuilder sb = new StringBuilder();
        for (String s: abilities) {
            sb.append(s);
        }
        return sb.toString();
    }
}
