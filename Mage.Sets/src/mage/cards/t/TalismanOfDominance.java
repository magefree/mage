
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jonubuu
 */
public final class TalismanOfDominance extends CardImpl {

    public TalismanOfDominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Add {U} or {B}. Talisman of Dominance deals 1 damage to you.
        Ability blueManaAbility = new BlueManaAbility();
        blueManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(blueManaAbility);
        Ability blackManaAbility = new BlackManaAbility();
        blackManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(blackManaAbility);
    }

    private TalismanOfDominance(final TalismanOfDominance card) {
        super(card);
    }

    @Override
    public TalismanOfDominance copy() {
        return new TalismanOfDominance(this);
    }
}
