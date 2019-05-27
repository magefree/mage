package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TalismanOfHierarchy extends CardImpl {

    public TalismanOfHierarchy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {W} or {B}. Talisman of Hierarchy deals 1 damage to you.
        Ability ability = new WhiteManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
        ability = new BlackManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
    }

    private TalismanOfHierarchy(final TalismanOfHierarchy card) {
        super(card);
    }

    @Override
    public TalismanOfHierarchy copy() {
        return new TalismanOfHierarchy(this);
    }
}
