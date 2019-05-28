package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TalismanOfConviction extends CardImpl {

    public TalismanOfConviction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {R} or {W}. Talisman of Conviction deals 1 damage to you.
        Ability ability = new RedManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
        ability = new WhiteManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
    }

    private TalismanOfConviction(final TalismanOfConviction card) {
        super(card);
    }

    @Override
    public TalismanOfConviction copy() {
        return new TalismanOfConviction(this);
    }
}
