
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterHistoricCard;

/**
 *
 * @author LevelX2
 */
public final class JhoirasFamiliar extends CardImpl {

    public JhoirasFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Historic spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostReductionControllerEffect(new FilterHistoricCard(), 1)
                        .setText("Historic spells you cast cost {1} less to cast. <i>(Artifacts, legendaries, and Sagas are historic.)</i>")));
    }

    private JhoirasFamiliar(final JhoirasFamiliar card) {
        super(card);
    }

    @Override
    public JhoirasFamiliar copy() {
        return new JhoirasFamiliar(this);
    }
}
