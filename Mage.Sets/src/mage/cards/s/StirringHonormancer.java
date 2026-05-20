package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StirringHonormancer extends CardImpl {

    public StirringHonormancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W/B}{B}");

        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When this creature enters, look at the top X cards of your library, where X is the number of creatures you control. Put one of those cards into your hand and the rest into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                CreaturesYouControlCount.SINGULAR, 1, PutCards.HAND, PutCards.GRAVEYARD
        )).addHint(CreaturesYouControlHint.instance));
    }

    private StirringHonormancer(final StirringHonormancer card) {
        super(card);
    }

    @Override
    public StirringHonormancer copy() {
        return new StirringHonormancer(this);
    }
}
