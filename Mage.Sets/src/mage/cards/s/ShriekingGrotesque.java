
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.target.TargetPlayer;

/**
 *
 * @author LoneFox

 */
public final class ShriekingGrotesque extends CardImpl {

    public ShriekingGrotesque(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Shrieking Grotesque enters the battlefield, if {B} was spent to cast it, target player discards a card.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, ManaWasSpentCondition.BLACK,
            "if {B} was spent to cast this spell, target player discards a card."));
    }

    private ShriekingGrotesque(final ShriekingGrotesque card) {
        super(card);
    }

    @Override
    public ShriekingGrotesque copy() {
        return new ShriekingGrotesque(this);
    }
}
