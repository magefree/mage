
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesColorOrColorsTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class PrismwakeMerrow extends CardImpl {

    public PrismwakeMerrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Prismwake Merrow enters the battlefield, target permanent becomes the color or colors of your choice until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BecomesColorOrColorsTargetEffect(Duration.EndOfTurn), false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

    }

    private PrismwakeMerrow(final PrismwakeMerrow card) {
        super(card);
    }

    @Override
    public PrismwakeMerrow copy() {
        return new PrismwakeMerrow(this);
    }
}
