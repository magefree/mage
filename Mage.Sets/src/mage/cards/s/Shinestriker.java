package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Shinestriker extends CardImpl {

    public Shinestriker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vivid -- When this creature enters, draw cards equal to the number of colors among permanents you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS)
                        .setText("draw cards equal to the number of colors among permanents you control")
        ).setAbilityWord(AbilityWord.VIVID).addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint()));
    }

    private Shinestriker(final Shinestriker card) {
        super(card);
    }

    @Override
    public Shinestriker copy() {
        return new Shinestriker(this);
    }
}
