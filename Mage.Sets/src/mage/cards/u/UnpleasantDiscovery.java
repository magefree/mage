package mage.cards.u;

import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.RevealHandEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class UnpleasantDiscovery extends CardImpl {
    public UnpleasantDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        //Each opponent reveals their hand, loses 1 life, and mills two cards.
        this.getSpellAbility().addEffect(new RevealHandEachPlayerEffect(TargetController.OPPONENT));
        this.getSpellAbility().addEffect(new LoseLifeOpponentsEffect(1).setText("and loses 1 life"));
        this.getSpellAbility().addEffect(new MillCardsEachPlayerEffect(2, TargetController.OPPONENT)
                .setText(" and mills two cards"));
    }

    public UnpleasantDiscovery(final UnpleasantDiscovery card) {
        super(card);
    }

    @Override
    public UnpleasantDiscovery copy() {
        return new UnpleasantDiscovery(this);
    }
}
