package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FarsightAdept extends CardImpl {

    public FarsightAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Farsight Adept enters the battlefield, you and target opponent each draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(1).setText("you")
        );
        ability.addEffect(new DrawCardTargetEffect(1)
                .setText("target opponent each draw a card").concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private FarsightAdept(final FarsightAdept card) {
        super(card);
    }

    @Override
    public FarsightAdept copy() {
        return new FarsightAdept(this);
    }
}
