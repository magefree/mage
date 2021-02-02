
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author TheElk801
 */
public final class GlowingAnemone extends CardImpl {

    public GlowingAnemone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.JELLYFISH);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Glowing Anemone enters the battlefield, you may return target land to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private GlowingAnemone(final GlowingAnemone card) {
        super(card);
    }

    @Override
    public GlowingAnemone copy() {
        return new GlowingAnemone(this);
    }
}
