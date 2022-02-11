
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AbolisherOfBloodlines extends CardImpl {

    public AbolisherOfBloodlines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature transforms into Abolisher of Bloodlines, target opponent sacrifices three creatures.
        Ability ability = new TransformIntoSourceTriggeredAbility(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, 3, "target opponent"
        ));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private AbolisherOfBloodlines(final AbolisherOfBloodlines card) {
        super(card);
    }

    @Override
    public AbolisherOfBloodlines copy() {
        return new AbolisherOfBloodlines(this);
    }
}
