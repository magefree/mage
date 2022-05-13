
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class PatronOfTheWild extends CardImpl {

    public PatronOfTheWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Morph {2}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{G}")));
        // When Patron of the Wild is turned face up, target creature gets +3/+3 until end of turn.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PatronOfTheWild(final PatronOfTheWild card) {
        super(card);
    }

    @Override
    public PatronOfTheWild copy() {
        return new PatronOfTheWild(this);
    }
}
