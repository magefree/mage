package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirjaJudgeOfValor extends CardImpl {

    public FirjaJudgeOfValor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you cast your second spell each turn, look at the top three cards of your library.
        // Put one of them into your hand and the rest into your graveyard.
        this.addAbility(new CastSecondSpellTriggeredAbility(new LookLibraryAndPickControllerEffect(
                3, 1, PutCards.HAND, PutCards.GRAVEYARD)));
    }

    private FirjaJudgeOfValor(final FirjaJudgeOfValor card) {
        super(card);
    }

    @Override
    public FirjaJudgeOfValor copy() {
        return new FirjaJudgeOfValor(this);
    }
}
