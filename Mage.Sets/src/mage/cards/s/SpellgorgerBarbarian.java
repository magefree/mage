
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class SpellgorgerBarbarian extends CardImpl {

    public SpellgorgerBarbarian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Spellgorger Barbarian enters the battlefield, discard a card at random.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardControllerEffect(1, true)));
        // When Spellgorger Barbarian leaves the battlefield, draw a card.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    private SpellgorgerBarbarian(final SpellgorgerBarbarian card) {
        super(card);
    }

    @Override
    public SpellgorgerBarbarian copy() {
        return new SpellgorgerBarbarian(this);
    }
}
