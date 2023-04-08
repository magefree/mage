package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArtisticRefusal extends CardImpl {

    public ArtisticRefusal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // * Draw two cards, then discard a card.
        this.getSpellAbility().addMode(new Mode(new DrawDiscardControllerEffect(2, 1)));
    }

    private ArtisticRefusal(final ArtisticRefusal card) {
        super(card);
    }

    @Override
    public ArtisticRefusal copy() {
        return new ArtisticRefusal(this);
    }
}
