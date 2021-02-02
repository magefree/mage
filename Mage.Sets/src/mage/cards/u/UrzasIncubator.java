
package mage.cards.u;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionAllOfChosenSubtypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author LoneFox
 */
public final class UrzasIncubator extends CardImpl {

    public UrzasIncubator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // As Urza's Incubator enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));
        // Creature spells of the chosen type cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostReductionAllOfChosenSubtypeEffect(new FilterCreatureCard("creature spells of the chosen type"), 2)));
    }

    private UrzasIncubator(final UrzasIncubator card) {
        super(card);
    }

    @Override
    public UrzasIncubator copy() {
        return new UrzasIncubator(this);
    }
}
