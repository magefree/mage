package mage.cards.r;

import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuthlessNegotiation extends CardImpl {

    public RuthlessNegotiation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Target opponent exiles a card from their hand. If this spell was cast from a graveyard, draw a card.
        this.getSpellAbility().addEffect(new ExileFromZoneTargetEffect(Zone.HAND, false));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), CastFromGraveyardSourceCondition.instance,
                "if this spell was cast from a graveyard, draw a card"
        ));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Flashback {4}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{B}")));
    }

    private RuthlessNegotiation(final RuthlessNegotiation card) {
        super(card);
    }

    @Override
    public RuthlessNegotiation copy() {
        return new RuthlessNegotiation(this);
    }
}
