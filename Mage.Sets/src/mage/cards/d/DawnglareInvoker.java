
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class DawnglareInvoker extends CardImpl {

    public DawnglareInvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new TapAllTargetPlayerControlsEffect(FILTER_PERMANENT_CREATURES),
                new ManaCostsImpl<>("{8}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DawnglareInvoker(final DawnglareInvoker card) {
        super(card);
    }

    @Override
    public DawnglareInvoker copy() {
        return new DawnglareInvoker(this);
    }
}
