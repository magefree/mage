
package mage.cards.c;

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
 * @author LoneFox
 */
public final class CorruptCourtOfficial extends CardImpl {

    public CorruptCourtOfficial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Corrupt Court Official enters the battlefield, target opponent discards a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private CorruptCourtOfficial(final CorruptCourtOfficial card) {
        super(card);
    }

    @Override
    public CorruptCourtOfficial copy() {
        return new CorruptCourtOfficial(this);
    }
}
