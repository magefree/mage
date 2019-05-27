package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TalismanOfResilience extends CardImpl {

    public TalismanOfResilience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {B} or {G}. Talisman of Resilience deals 1 damage to you.
        Ability ability = new BlackManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
        ability = new GreenManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
    }

    private TalismanOfResilience(final TalismanOfResilience card) {
        super(card);
    }

    @Override
    public TalismanOfResilience copy() {
        return new TalismanOfResilience(this);
    }
}
