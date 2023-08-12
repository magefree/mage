
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.WinsCoinFlipTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.FlipUntilLoseEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author TheElk801
 */
public final class ZndrspltEyeOfWisdom extends CardImpl {

    public ZndrspltEyeOfWisdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Partner with Okaun, Eye of Chaos (When this creature enters the battlefield, target player may put Okaun into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Okaun, Eye of Chaos", true));

        // At the beginning of combat on your turn, flip a coin until you lose a flip.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new FlipUntilLoseEffect(), TargetController.YOU, false));

        // Whenever a player wins a coin flip, draw a card.
        this.addAbility(new WinsCoinFlipTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private ZndrspltEyeOfWisdom(final ZndrspltEyeOfWisdom card) {
        super(card);
    }

    @Override
    public ZndrspltEyeOfWisdom copy() {
        return new ZndrspltEyeOfWisdom(this);
    }
}