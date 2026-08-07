package mage.cards.t;

import java.util.UUID;

import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author muz
 */
public final class TidingsOfWar extends CardImpl {

    public TidingsOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Amass Goblins 1. If this spell was cast from a graveyard, amass Goblins 3 instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new AmassEffect(3, SubType.GOBLIN),
            new AmassEffect(1, SubType.GOBLIN),
            CastFromGraveyardSourceCondition.instance,
            "Amass Goblins 1. If this spell was cast from a graveyard, amass Goblins 3 instead."
        ));

        // Flashback {3}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{R}")));

    }

    private TidingsOfWar(final TidingsOfWar card) {
        super(card);
    }

    @Override
    public TidingsOfWar copy() {
        return new TidingsOfWar(this);
    }
}
