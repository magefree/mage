package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.OfficerToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class WorfSonOfMogh extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.OFFICER, "Officers");

    public WorfSonOfMogh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Other Officers you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
            1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Seek out -- Whenever you draw your second card each turn, create a 1/1 red Officer creature token.
        this.addAbility(new DrawNthCardTriggeredAbility(new CreateTokenEffect(new OfficerToken()))
            .withFlavorWord("Seek out"));
    }

    private WorfSonOfMogh(final WorfSonOfMogh card) {
        super(card);
    }

    @Override
    public WorfSonOfMogh copy() {
        return new WorfSonOfMogh(this);
    }
}
