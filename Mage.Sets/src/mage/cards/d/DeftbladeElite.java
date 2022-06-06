
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.abilities.effects.common.PreventCombatDamageToSourceEffect;
import mage.abilities.keyword.ProvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class DeftbladeElite extends CardImpl {

    public DeftbladeElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Provoke
        this.addAbility(new ProvokeAbility());
        // {1}{W}: Prevent all combat damage that would be dealt to and dealt by Deftblade Elite this turn.
        Effect effect = new PreventCombatDamageToSourceEffect(Duration.EndOfTurn);
        effect.setText("Prevent all combat damage that would be dealt to");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}{W}"));
        effect = new PreventCombatDamageBySourceEffect(Duration.EndOfTurn);
        effect.setText("and dealt by {this} this turn");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private DeftbladeElite(final DeftbladeElite card) {
        super(card);
    }

    @Override
    public DeftbladeElite copy() {
        return new DeftbladeElite(this);
    }
}
