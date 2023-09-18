
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class NoggleRansacker extends CardImpl {

    public NoggleRansacker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U/R}");
        this.subtype.add(SubType.NOGGLE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Noggle Ransacker enters the battlefield, each player draws two cards, then discards a card at random.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardAllEffect(2));
        ability.addEffect(new DiscardEachPlayerEffect(1, true).setText(", then discards a card at random"));
        this.addAbility(ability);
    }

    private NoggleRansacker(final NoggleRansacker card) {
        super(card);
    }

    @Override
    public NoggleRansacker copy() {
        return new NoggleRansacker(this);
    }
}
