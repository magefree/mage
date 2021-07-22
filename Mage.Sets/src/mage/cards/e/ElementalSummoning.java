package mage.cards.e;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Elemental44Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElementalSummoning extends CardImpl {

    public ElementalSummoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U/R}{U/R}");

        this.subtype.add(SubType.LESSON);

        // Create a 4/4 blue and red Elemental creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Elemental44Token()));
    }

    private ElementalSummoning(final ElementalSummoning card) {
        super(card);
    }

    @Override
    public ElementalSummoning copy() {
        return new ElementalSummoning(this);
    }
}
