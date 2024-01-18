package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaerieSnoop extends CardImpl {

    public FaerieSnoop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Disguise {1}{U/B}{U/B}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{1}{U/B}{U/B}")));

        // When Faerie Snoop is turned face up, look at the top two cards of your library. Put one into your hand and the other into your graveyard.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new LookLibraryAndPickControllerEffect(
                2, 1, PutCards.HAND, PutCards.GRAVEYARD
        )));
    }

    private FaerieSnoop(final FaerieSnoop card) {
        super(card);
    }

    @Override
    public FaerieSnoop copy() {
        return new FaerieSnoop(this);
    }
}
