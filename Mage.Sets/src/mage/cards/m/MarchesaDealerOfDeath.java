package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
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
public final class MarchesaDealerOfDeath extends CardImpl {

    public MarchesaDealerOfDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you commit a crime, you may pay {1}. If you do, look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.
        this.addAbility(new CommittedCrimeTriggeredAbility(new DoIfCostPaid(
                new LookLibraryAndPickControllerEffect(
                        2, 1, PutCards.HAND, PutCards.GRAVEYARD
                ), new GenericManaCost(1)
        )));
    }

    private MarchesaDealerOfDeath(final MarchesaDealerOfDeath card) {
        super(card);
    }

    @Override
    public MarchesaDealerOfDeath copy() {
        return new MarchesaDealerOfDeath(this);
    }
}
