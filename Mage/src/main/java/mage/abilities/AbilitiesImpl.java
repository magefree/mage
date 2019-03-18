
package mage.abilities;

import java.util.*;
import java.util.stream.Collectors;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.ThreadLocalStringBuilder;
import org.apache.log4j.Logger;

/**
 * @param <T>
 * @author BetaSteward_at_googlemail.com
 */
public class AbilitiesImpl<T extends Ability> extends ArrayList<T> implements Abilities<T> {

    private static final Logger logger = Logger.getLogger(AbilitiesImpl.class);

    private static final ThreadLocalStringBuilder threadLocalBuilder = new ThreadLocalStringBuilder(200);

    public AbilitiesImpl() {
    }

    public AbilitiesImpl(T... abilities) {
        addAll(Arrays.asList(abilities));
    }

    public AbilitiesImpl(final AbilitiesImpl<T> abilities) {
        for (T ability : abilities) {
            this.add((T) ability.copy());
        }
    }

    @Override
    public AbilitiesImpl<T> copy() {
        return new AbilitiesImpl<>(this);
    }

    @Override
    public List<String> getRules(String source) {
        return getRules(source, true);
    }

    @Override
    public List<String> getRules(String source, boolean capitalize) {
        List<String> rules = new ArrayList<>();

        for (T ability : this) {
            if (!ability.getRuleVisible()) {
                continue;
            }
            if (!(ability instanceof SpellAbility || ability instanceof PlayLandAbility)) {
                String rule = ability.getRule();
                if (rule != null && rule.length() > 3) {
                    if (capitalize) {
                        rule = Character.toUpperCase(rule.charAt(0)) + rule.substring(1);
                    }
                    if (ability.getRuleAtTheTop()) {
                        rules.add(0, rule);
                    } else {
                        rules.add(rule);
                    }
                }
                continue;
            }
            if (ability instanceof SpellAbility) {
                if (ability.getAdditionalCostsRuleVisible() && !ability.getCosts().isEmpty()) {
                    StringBuilder sbRule = threadLocalBuilder.get();
                    for (Cost cost : ability.getCosts()) {
                        if (cost.getText() != null && !cost.getText().isEmpty()) {
                            if (!cost.getText().startsWith("As an additional cost")) {
                                sbRule.append("As an additional cost to cast this spell, ");
                            }
                            sbRule.append(cost.getText()).append(".<br>");
                        }
                    }
                    rules.add(sbRule.toString());
                }
                String rule = ability.getRule();
                if (rule != null) {
                    if (!rule.isEmpty()) {
                        rules.add(Character.toUpperCase(rule.charAt(0)) + rule.substring(1));
                    }
                } else { // logging so we can still can be made aware of rule problems a card has
                    String cardName = ((SpellAbility) ability).getCardName();
                    logger.fatal("Error in rule text generation of " + cardName + ": Create a bug report or fix the source code");
                }
            }
        }

        return rules;
    }

    @Override
    public Abilities<ActivatedAbility> getActivatedAbilities(Zone zone) {
        return stream()
                .filter(ability -> ability instanceof ActivatedAbility)
                .filter(ability -> ability.getZone().match(zone))
                .map(ability -> (ActivatedAbility) ability)
                .collect(Collectors.toCollection(AbilitiesImpl::new));

    }

    @Override
    public Abilities<ActivatedAbility> getPlayableAbilities(Zone zone) {
        return stream()
                .filter(ability -> (ability instanceof ActivatedAbility))
                .filter(ability -> ability.getZone().match(zone))
                .map(ability -> (ActivatedAbility) ability)
                .collect(Collectors.toCollection(AbilitiesImpl::new));

    }

    @Override
    public Abilities<ActivatedManaAbilityImpl> getActivatedManaAbilities(Zone zone) {
        return stream()
                .filter(ability -> ability instanceof ActivatedManaAbilityImpl)
                .filter(ability -> ability.getZone().match(zone))
                .map(ability -> (ActivatedManaAbilityImpl) ability)
                .collect(Collectors.toCollection(AbilitiesImpl::new));

    }

    @Override
    public Abilities<ActivatedManaAbilityImpl> getAvailableActivatedManaAbilities(Zone zone, Game game) {
        return stream()
                .filter(ability -> ability instanceof ActivatedManaAbilityImpl)
                .filter(ability -> ability.getZone().match(zone))
                .filter(ability -> (((ActivatedManaAbilityImpl) ability).canActivate(ability.getControllerId(), game).canActivate()))
                .map(ability -> (ActivatedManaAbilityImpl) ability)
                .collect(Collectors.toCollection(AbilitiesImpl::new));

    }

    @Override
    public Abilities<Ability> getManaAbilities(Zone zone) {
        return stream()
                .filter(ability -> ability.getAbilityType() == AbilityType.MANA)
                .filter(ability -> ability.getZone().match(zone))
                .collect(Collectors.toCollection(AbilitiesImpl::new));

    }

    @Override
    public Abilities<EvasionAbility> getEvasionAbilities() {
        return stream()
                .filter(ability -> ability instanceof EvasionAbility)
                .map(ability -> (EvasionAbility) ability)
                .collect(Collectors.toCollection(AbilitiesImpl::new));

    }

    @Override
    public Abilities<StaticAbility> getStaticAbilities(Zone zone) {
        return stream()
                .filter(ability -> ability instanceof StaticAbility)
                .filter(ability -> ability.getZone().match(zone))
                .map(ability -> (StaticAbility) ability)
                .collect(Collectors.toCollection(AbilitiesImpl::new));

    }

    @Override
    public Abilities<TriggeredAbility> getTriggeredAbilities(Zone zone) {
        Abilities<TriggeredAbility> zonedAbilities = new AbilitiesImpl<>();
        for (T ability : this) {
            if (ability instanceof TriggeredAbility && ability.getZone().match(zone)) {
                zonedAbilities.add((TriggeredAbility) ability);
            } else if (ability instanceof ZoneChangeTriggeredAbility) {
                ZoneChangeTriggeredAbility zcAbility = (ZoneChangeTriggeredAbility) ability;
                if (zcAbility.getToZone() != null && zcAbility.getToZone().match(zone)) {
                    zonedAbilities.add((ZoneChangeTriggeredAbility) ability);
                }
            }
        }
        return zonedAbilities;
    }

    @Override
    public Abilities<ProtectionAbility> getProtectionAbilities() {
        return stream()
                .filter(ability -> ability instanceof ProtectionAbility)
                .map(ability -> (ProtectionAbility) ability)
                .collect(Collectors.toCollection(AbilitiesImpl::new));

    }

    @Override
    public void setControllerId(UUID controllerId) {
        for (Ability ability : this) {
            ability.setControllerId(controllerId);
        }
    }

    @Override
    public void setSourceId(UUID sourceId) {
        for (Ability ability : this) {
            ability.setSourceId(sourceId);
        }
    }

    @Override
    public void newId() {
        for (Ability ability : this) {
            ability.newId();
        }
    }

    @Override
    public void newOriginalId() {
        for (Ability ability : this) {
            ability.newOriginalId();
        }
    }

    @Override
    public boolean contains(T ability) {
        for (Iterator<T> iterator = this.iterator(); iterator.hasNext();) { // simple loop can cause java.util.ConcurrentModificationException
            T test = iterator.next();
            // Checking also by getRule() without other restrictions is a problem when a triggered ability will be copied to a permanent that had the same ability
            // already before the copy. Because then it keeps the triggered ability twice and it triggers twice.
            // e.g. 2 Biovisonary and one enchanted with Infinite Reflection
            if (ability.getId().equals(test.getId())) {
                return true;
            }
            if (ability.getOriginalId().equals(test.getId())) {
                return true;
            }
            if (ability instanceof MageSingleton && test instanceof MageSingleton && ability.getRule().equals(test.getRule())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsRule(T ability) {
        return stream().anyMatch(rule -> rule.getRule().equals(ability.getRule()));
    }

    @Override
    public boolean containsAll(Abilities<T> abilities) {

        if (this.size() < abilities.size()) {
            return false;
        }
        for (T ability : abilities) {
            if (!contains(ability)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean containsKey(UUID abilityId) {
        return stream().anyMatch(ability -> abilityId.equals(ability.getId()));
    }

    @Override
    public boolean containsClass(Class classObject) {
        return stream().anyMatch(ability -> ability.getClass().equals(classObject));
    }

    public Optional<T> get(UUID abilityId) {
        return stream().filter(ability -> ability.getId().equals(abilityId)).findFirst();
    }

    @Override
    public int getOutcomeTotal() {
        return stream().mapToInt(ability -> ability.getEffects().getOutcomeTotal()).sum();
    }

    @Override
    public String getValue() {
        List<String> abilities = new ArrayList<>();
        for (T ability : this) {
            if (ability.toString() != null) {
                abilities.add(ability.toString());
            }
        }
        Collections.sort(abilities);
        StringBuilder sb = threadLocalBuilder.get();
        for (String s : abilities) {
            sb.append(s);
        }
        return sb.toString();
    }
}
