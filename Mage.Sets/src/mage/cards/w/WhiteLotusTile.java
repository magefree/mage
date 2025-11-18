package mage.cards.w;

import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.GreatestSharedCreatureTypeCount;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WhiteLotusTile extends CardImpl {

    public WhiteLotusTile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // This artifact enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add X mana of any one color, where X is the greatest number of creatures you control that have a creature type in common.
        this.addAbility(new DynamicManaAbility(
                Mana.AnyMana(1), GreatestSharedCreatureTypeCount.instance, new TapSourceCost(),
                "Add X mana of any one color, where X is the greatest number of " +
                        "creatures you control that have a creature type in common.", true
        ).addHint(GreatestSharedCreatureTypeCount.getHint()));
    }

    private WhiteLotusTile(final WhiteLotusTile card) {
        super(card);
    }

    @Override
    public WhiteLotusTile copy() {
        return new WhiteLotusTile(this);
    }
}
