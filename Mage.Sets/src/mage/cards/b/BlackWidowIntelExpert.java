package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class BlackWidowIntelExpert extends CardImpl {

    public BlackWidowIntelExpert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Black Widow deals combat damage to an opponent, you and that player each draw two cards.
        Ability ability = new DealsDamageToOpponentTriggeredAbility(
                new DrawCardSourceControllerEffect(2).setText("you"), false, true, true
        );
        ability.addEffect(new DrawCardTargetEffect(2).setText("and that player each draw two cards"));
        this.addAbility(ability);
    }

    private BlackWidowIntelExpert(final BlackWidowIntelExpert card) {
        super(card);
    }

    @Override
    public BlackWidowIntelExpert copy() {
        return new BlackWidowIntelExpert(this);
    }
}
