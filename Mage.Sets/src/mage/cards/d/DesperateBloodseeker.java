package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DesperateBloodseeker extends CardImpl {

    public DesperateBloodseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Desperate Bloodseeker enters the battlefield, target player mills two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsTargetEffect(2));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DesperateBloodseeker(final DesperateBloodseeker card) {
        super(card);
    }

    @Override
    public DesperateBloodseeker copy() {
        return new DesperateBloodseeker(this);
    }
}
