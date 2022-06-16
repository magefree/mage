
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class ShaleskinPlower extends CardImpl {

    public ShaleskinPlower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Morph {4}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{R}")));
        // When Shaleskin Plower is turned face up, destroy target land.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private ShaleskinPlower(final ShaleskinPlower card) {
        super(card);
    }

    @Override
    public ShaleskinPlower copy() {
        return new ShaleskinPlower(this);
    }
}
