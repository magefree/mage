package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author muz
 */
public final class AbyssalLotus extends CardImpl {

    public AbyssalLotus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // This artifact enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Descend 8 -- {T}, Exile this artifact: Add three mana of any one color. Activate only if there are eight or more permanent cards in your graveyard.
        SimpleManaAbility ability = new SimpleManaAbility(
            Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3), new TapSourceCost()
        );
        ability.addCost(new ExileSourceCost());
        ability.setCondition(DescendCondition.EIGHT);
        ability.appendToRule(" Activate only if there are eight or more permanent cards in your graveyard.");
        ability.setAbilityWord(AbilityWord.DESCEND_8);
        ability.addHint(DescendCondition.getHint());
        this.addAbility(ability);
    }

    private AbyssalLotus(final AbyssalLotus card) {
        super(card);
    }

    @Override
    public AbyssalLotus copy() {
        return new AbyssalLotus(this);
    }
}
