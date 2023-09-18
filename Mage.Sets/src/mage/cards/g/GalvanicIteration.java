package mage.cards.g;

import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GalvanicIteration extends CardImpl {

    public GalvanicIteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{R}");

        // When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()));

        // Flashback {1}{U}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{U}{R}")));
    }

    private GalvanicIteration(final GalvanicIteration card) {
        super(card);
    }

    @Override
    public GalvanicIteration copy() {
        return new GalvanicIteration(this);
    }
}
