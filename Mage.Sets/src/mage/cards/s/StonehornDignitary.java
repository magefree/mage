package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SkipCombatStepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author nantuko
 */
public final class StonehornDignitary extends CardImpl {
    
    public StonehornDignitary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.SOLDIER);
        
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Stonehorn Dignitary enters the battlefield, target opponent skips their next combat phase.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SkipCombatStepEffect(Duration.OneUse).setText("target opponent skips their next combat phase."));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }
    
    private StonehornDignitary(final StonehornDignitary card) {
        super(card);
    }
    
    @Override
    public StonehornDignitary copy() {
        return new StonehornDignitary(this);
    }
}
