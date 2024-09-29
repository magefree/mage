
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CantBeTargetedSourceEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.filter.FilterStackObject;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author igout
 */
public final class SuqAtaFirewalker extends CardImpl {

    private static final FilterObject filterRed = new FilterStackObject("red spells or abilities from red sources");

    static {
        filterRed.add(new ColorPredicate(ObjectColor.RED));
    }

    public SuqAtaFirewalker(UUID cardId, CardSetInfo cardSetInfo) {
        super(cardId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.WIZARD);
        power = new MageInt(0);
        toughness = new MageInt(1);
        color.setBlue(true);

        // Suq'Ata Firewalker can't be the target of red spells or abilities from red sources.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeTargetedSourceEffect(filterRed, Duration.WhileOnBattlefield)));

        //{T}: Suq'Ata Firewalker deals 1 damage to any target.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SuqAtaFirewalker(final SuqAtaFirewalker other) {
        super(other);
    }

    @Override
    public SuqAtaFirewalker copy() {
        return new SuqAtaFirewalker(this);
    }
}
