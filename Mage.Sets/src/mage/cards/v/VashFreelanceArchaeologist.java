package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author muz
 */
public final class VashFreelanceArchaeologist extends CardImpl {

    public VashFreelanceArchaeologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, surveil 1.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SurveilEffect(1)));

        // Whenever a card leaves your graveyard, draw a card. This ability triggers only once each turn.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new DrawCardSourceControllerEffect(1))
            .setTriggerPhrase("Whenever a card leaves your graveyard, ").setTriggersLimitEachTurn(1));
    }

    private VashFreelanceArchaeologist(final VashFreelanceArchaeologist card) {
        super(card);
    }

    @Override
    public VashFreelanceArchaeologist copy() {
        return new VashFreelanceArchaeologist(this);
    }
}
