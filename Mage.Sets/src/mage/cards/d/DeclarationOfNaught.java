
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetAdjustment;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public final class DeclarationOfNaught extends CardImpl {

    static final private FilterSpell filter = new FilterSpell("spell with the chosen name");

    public DeclarationOfNaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");

        // As Declaration of Naught enters the battlefield, name a card.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL)));

        // {U}: Counter target spell with the chosen name.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl("{U}"));
        ability.setTargetAdjustment(TargetAdjustment.CHOSEN_NAME);
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    public DeclarationOfNaught(final DeclarationOfNaught card) {
        super(card);
    }

    @Override
    public DeclarationOfNaught copy() {
        return new DeclarationOfNaught(this);
    }
}
