package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessConditionEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;

/**
 *
 * @author LevelX2
 */
public final class CourtHussar extends CardImpl {

    public CourtHussar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // When Court Hussar enters the battlefield, look at the top three cards of your library, then put one of them into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(3, 1, PutCards.HAND, PutCards.BOTTOM_ANY)));
        // When Court Hussar enters the battlefield, sacrifice it unless {W} was spent to cast it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessConditionEffect(ManaWasSpentCondition.WHITE), false));
    }

    private CourtHussar(final CourtHussar card) {
        super(card);
    }

    @Override
    public CourtHussar copy() {
        return new CourtHussar(this);
    }
}
