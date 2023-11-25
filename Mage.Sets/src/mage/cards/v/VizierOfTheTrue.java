
package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExertCreatureControllerTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author spjspj
 */
public final class VizierOfTheTrue extends CardImpl {

    public VizierOfTheTrue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // You may exert Vizier of the True as it attacks.
        this.addAbility(new ExertAbility(null, false));

        // Whenever you exert a creature, tap target creature an opponent controls.
        Ability ability = new ExertCreatureControllerTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private VizierOfTheTrue(final VizierOfTheTrue card) {
        super(card);
    }

    @Override
    public VizierOfTheTrue copy() {
        return new VizierOfTheTrue(this);
    }
}
