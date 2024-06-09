
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.TargetPermanent;

/**
 *
 * @author Plopman
 */
public final class MangaraOfCorondor extends CardImpl {

    public MangaraOfCorondor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Exile Mangara of Corondor and target permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileSourceEffect(), new TapSourceCost());
        ability.addEffect(new ExileTargetEffect().setText("and target permanent"));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private MangaraOfCorondor(final MangaraOfCorondor card) {
        super(card);
    }

    @Override
    public MangaraOfCorondor copy() {
        return new MangaraOfCorondor(this);
    }
}
