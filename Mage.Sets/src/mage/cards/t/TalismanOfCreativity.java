package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TalismanOfCreativity extends CardImpl {

    public TalismanOfCreativity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {U} or {R}. Talisman of Creativity deals 1 damage to you.
        Ability ability = new BlueManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
        ability = new RedManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
    }

    private TalismanOfCreativity(final TalismanOfCreativity card) {
        super(card);
    }

    @Override
    public TalismanOfCreativity copy() {
        return new TalismanOfCreativity(this);
    }
}
