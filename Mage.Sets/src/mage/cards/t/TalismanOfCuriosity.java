package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TalismanOfCuriosity extends CardImpl {

    public TalismanOfCuriosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {G} or {U}. Talisman of Curiosity deals 1 damage to you.
        Ability ability = new GreenManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
        ability = new BlueManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
    }

    private TalismanOfCuriosity(final TalismanOfCuriosity card) {
        super(card);
    }

    @Override
    public TalismanOfCuriosity copy() {
        return new TalismanOfCuriosity(this);
    }
}
