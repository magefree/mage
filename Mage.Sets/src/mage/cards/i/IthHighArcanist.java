
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author LoneFox
 */
public final class IthHighArcanist extends CardImpl {

    public IthHighArcanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // {T}: Untap target attacking creature. Prevent all combat damage that would be dealt to and dealt by that creature this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new TapSourceCost());                                                                                           Effect effect = new PreventDamageByTargetEffect(Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage that would be dealt to");
        ability.addEffect(effect);
        effect = new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE, true);
        effect.setText("and dealt by that creature this turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
        // Suspend 4-{W}{U}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{W}{U}"), this));
    }

    private IthHighArcanist(final IthHighArcanist card) {
        super(card);
    }

    @Override
    public IthHighArcanist copy() {
        return new IthHighArcanist(this);
    }
}
