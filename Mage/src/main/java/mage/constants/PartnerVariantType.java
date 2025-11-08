package mage.constants;

import mage.abilities.StaticAbility;
import mage.cards.Card;
import mage.util.CardUtil;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public enum PartnerVariantType {
    FATHER_AND_SON("Father & son"),
    SURVIVORS("Survivors"),
    CHARACTER_SELECT("Character select");

    private final String name;

    PartnerVariantType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public PartnerVariantAbility makeAbility() {
        return new PartnerVariantAbility(this);
    }

    private static Set<PartnerVariantType> getTypes(Card card) {
        return CardUtil
                .castStream(card.getAbilities(), PartnerVariantAbility.class)
                .map(PartnerVariantAbility::getType)
                .collect(Collectors.toSet());
    }

    public static boolean checkCommanders(Card commander1, Card commander2) {
        Set<PartnerVariantType> types1 = getTypes(commander1);
        if (types1.isEmpty()) {
            return false;
        }
        Set<PartnerVariantType> types2 = getTypes(commander2);
        if (types2.isEmpty()) {
            return false;
        }
        types1.retainAll(types2);
        return !types1.isEmpty();
    }
}

class PartnerVariantAbility extends StaticAbility {

    private final PartnerVariantType type;

    PartnerVariantAbility(PartnerVariantType type) {
        super(Zone.BATTLEFIELD, null);
        this.type = type;
    }

    private PartnerVariantAbility(final PartnerVariantAbility ability) {
        super(ability);
        this.type = ability.type;
    }

    @Override
    public PartnerVariantAbility copy() {
        return new PartnerVariantAbility(this);
    }

    public PartnerVariantType getType() {
        return type;
    }

    @Override
    public String getRule() {
        return "Partner&mdash;" + type + " <i>(You can have two commanders if both have this ability.)</i>";
    }
}
