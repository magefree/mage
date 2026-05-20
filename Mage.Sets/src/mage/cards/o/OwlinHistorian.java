package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OwlinHistorian extends CardImpl {

    public OwlinHistorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1)));

        // Whenever one or more cards leave your graveyard, this creature gets +1/+1 until end of turn.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn)));
    }

    private OwlinHistorian(final OwlinHistorian card) {
        super(card);
    }

    @Override
    public OwlinHistorian copy() {
        return new OwlinHistorian(this);
    }
}
