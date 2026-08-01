package mage.cards.p;

import java.util.UUID;

import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class PlunderTheTrollshaws extends CardImpl {

    public PlunderTheTrollshaws(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Draw a card. If this spell was cast from a graveyard, draw two cards instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new DrawCardSourceControllerEffect(2),
            new DrawCardSourceControllerEffect(1),
            CastFromGraveyardSourceCondition.instance,
            "Draw a card. If this spell was cast from a graveyard, draw two cards instead."
        ));

        // Flashback {3}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{U}")));
    }

    private PlunderTheTrollshaws(final PlunderTheTrollshaws card) {
        super(card);
    }

    @Override
    public PlunderTheTrollshaws copy() {
        return new PlunderTheTrollshaws(this);
    }
}
