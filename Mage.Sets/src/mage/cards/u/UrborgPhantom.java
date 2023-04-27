
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.abilities.effects.common.PreventCombatDamageToSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class UrborgPhantom extends CardImpl {

    public UrborgPhantom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Urborg Phantom can't block.
        this.addAbility(new CantBlockAbility());
        // {U}: Prevent all combat damage that would be dealt to and dealt by Urborg Phantom this turn.
        Effect effect = new PreventCombatDamageToSourceEffect(Duration.EndOfTurn);
        effect.setText("Prevent all combat damage that would be dealt to");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{U}"));
        effect = new PreventCombatDamageBySourceEffect(Duration.EndOfTurn);
        effect.setText("and dealt by {this} this turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private UrborgPhantom(final UrborgPhantom card) {
        super(card);
    }

    @Override
    public UrborgPhantom copy() {
        return new UrborgPhantom(this);
    }
}
