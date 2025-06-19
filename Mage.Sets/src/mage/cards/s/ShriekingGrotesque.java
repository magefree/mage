package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class ShriekingGrotesque extends CardImpl {

    public ShriekingGrotesque(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Shrieking Grotesque enters the battlefield, if {B} was spent to cast it, target player discards a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1)).withInterveningIf(ManaWasSpentCondition.BLACK);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ShriekingGrotesque(final ShriekingGrotesque card) {
        super(card);
    }

    @Override
    public ShriekingGrotesque copy() {
        return new ShriekingGrotesque(this);
    }
}
