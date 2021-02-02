
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class BrutalNightstalker extends CardImpl {

    public BrutalNightstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.NIGHTSTALKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Brutal Nightstalker enters the battlefield, you may have target opponent discard a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1), true);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BrutalNightstalker(final BrutalNightstalker card) {
        super(card);
    }

    @Override
    public BrutalNightstalker copy() {
        return new BrutalNightstalker(this);
    }
}
