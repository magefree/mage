package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TalasLookout extends CardImpl {

    public TalasLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Talas Lookout dies, look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.
        this.addAbility(new DiesSourceTriggeredAbility(new LookLibraryAndPickControllerEffect(
                2, 1,
                LookLibraryControllerEffect.PutCards.HAND,
                LookLibraryControllerEffect.PutCards.GRAVEYARD
        )));
    }

    private TalasLookout(final TalasLookout card) {
        super(card);
    }

    @Override
    public TalasLookout copy() {
        return new TalasLookout(this);
    }
}
