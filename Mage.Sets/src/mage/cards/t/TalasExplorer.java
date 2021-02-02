
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class TalasExplorer extends CardImpl {

    public TalasExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Talas Explorer enters the battlefield, look at target opponent's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LookAtTargetPlayerHandEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TalasExplorer(final TalasExplorer card) {
        super(card);
    }

    @Override
    public TalasExplorer copy() {
        return new TalasExplorer(this);
    }
}
