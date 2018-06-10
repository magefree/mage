
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 * @author JayDi85
 */
public final class ProteanRaider extends CardImpl {

    public ProteanRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Raid</i> &mdash; If you attacked with a creature this turn, you may have Protean Raider enter the battlefield as a copy of any creature on the battlefield.
        Ability ability = new EntersBattlefieldAbility(new CopyPermanentEffect(), true, RaidCondition.instance,
                "<i>Raid</i> &mdash; If you attacked with a creature this turn, you may have {this} enter the battlefield as a copy of any creature on the battlefield.", "");
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    public ProteanRaider(final ProteanRaider card) {
        super(card);
    }

    @Override
    public ProteanRaider copy() {
        return new ProteanRaider(this);
    }
}
