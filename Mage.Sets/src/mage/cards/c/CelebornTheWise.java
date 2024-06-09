package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetScryAmount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CelebornTheWise extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "Elves");

    public CelebornTheWise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you attack with one or more Elves, scry 1.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new ScryEffect(1, false), 1, filter
        ));

        // Whenever you scry, Celeborn the Wise gets +1/+1 until end of turn for each card you looked at while scrying this way.
        this.addAbility(new ScryTriggeredAbility(new BoostSourceEffect(
                GetScryAmount.instance, GetScryAmount.instance, Duration.EndOfTurn
        )));
    }

    private CelebornTheWise(final CelebornTheWise card) {
        super(card);
    }

    @Override
    public CelebornTheWise copy() {
        return new CelebornTheWise(this);
    }
}
