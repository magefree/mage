package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryTopCardTargetPlayerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author weirddan455
 */
public final class DraugrThoughtThief extends CardImpl {

    public DraugrThoughtThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Draugr Thought-Thief enters the battlefield, look at the top card of target player's library. You may put that card into their graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LookLibraryTopCardTargetPlayerEffect(1, true));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DraugrThoughtThief(final DraugrThoughtThief card) {
        super(card);
    }

    @Override
    public DraugrThoughtThief copy() {
        return new DraugrThoughtThief(this);
    }
}
