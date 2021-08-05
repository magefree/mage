package mage.cards.f;

import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FracturedSanity extends CardImpl {

    public FracturedSanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{U}{U}");

        // Each opponent mills fourteen cards.
        this.getSpellAbility().addEffect(new MillCardsEachPlayerEffect(14, TargetController.OPPONENT));

        // Cycling {1}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{U}")));

        // When you cycle Fractured Sanity, each opponent mills four cards.
        this.addAbility(new CycleTriggeredAbility(new MillCardsEachPlayerEffect(4, TargetController.OPPONENT)));
    }

    private FracturedSanity(final FracturedSanity card) {
        super(card);
    }

    @Override
    public FracturedSanity copy() {
        return new FracturedSanity(this);
    }
}
