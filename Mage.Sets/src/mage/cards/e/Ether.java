package mage.cards.e;

import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ether extends CardImpl {

    public Ether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{U}");

        // {T}, Exile this artifact: Add {U}. When you next cast an instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        BasicManaAbility ability = new BlueManaAbility();
        ability.addCost(new ExileSourceCost());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()));
        ability.setUndoPossible(false); // exiles itself and creates a delayed trigger so undo should be disabled
        this.addAbility(ability);
    }

    private Ether(final Ether card) {
        super(card);
    }

    @Override
    public Ether copy() {
        return new Ether(this);
    }
}
