package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EssenceOfOrthodoxy extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.PHYREXIAN, "Phyrexian");

    public EssenceOfOrthodoxy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Essence of Orthodoxy or another Phyrexian enters the battlefield under your control, incubate 2.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new IncubateEffect(2), filter, false, true
        ));
    }

    private EssenceOfOrthodoxy(final EssenceOfOrthodoxy card) {
        super(card);
    }

    @Override
    public EssenceOfOrthodoxy copy() {
        return new EssenceOfOrthodoxy(this);
    }
}
