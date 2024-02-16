package mage.cards.l;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LivingDeathEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class LivingEnd extends CardImpl {

    public LivingEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setBlack(true);

        // Suspend 3-{2}{B}{B}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{2}{B}{B}"), this));
        // Each player exiles all creature cards from their graveyard, then sacrifices all creatures
        // they control, then puts all cards they exiled this way onto the battlefield.
        this.getSpellAbility().addEffect(new LivingDeathEffect());
    }

    private LivingEnd(final LivingEnd card) {
        super(card);
    }

    @Override
    public LivingEnd copy() {
        return new LivingEnd(this);
    }
}
