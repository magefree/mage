
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class CoralTrickster extends CardImpl {

    public CoralTrickster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Morph {U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{U}")));
        // When Coral Trickster is turned face up, you may tap or untap target permanent.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new MayTapOrUntapTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private CoralTrickster(final CoralTrickster card) {
        super(card);
    }

    @Override
    public CoralTrickster copy() {
        return new CoralTrickster(this);
    }
}
