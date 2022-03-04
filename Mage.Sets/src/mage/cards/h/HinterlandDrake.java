
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBlockCreaturesSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Styxo
 */
public final class HinterlandDrake extends CardImpl {

    public HinterlandDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Hinterland Drake can't block artifact creatures.
        Effect effect = new CantBlockCreaturesSourceEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE);
        effect.setText("{this} can't block artifact creatures");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private HinterlandDrake(final HinterlandDrake card) {
        super(card);
    }

    @Override
    public HinterlandDrake copy() {
        return new HinterlandDrake(this);
    }
}
