
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.SnakeToken;

/**
 *
 * @author LoneFox
 */
public final class SnakePit extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a blue or black spell");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLUE), new ColorPredicate(ObjectColor.BLACK)));
    }

    public SnakePit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}");

        // Whenever an opponent casts a blue or black spell, you may create a 1/1 green Snake creature token.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new CreateTokenEffect(new SnakeToken()), filter, true));
    }

    private SnakePit(final SnakePit card) {
        super(card);
    }

    @Override
    public SnakePit copy() {
        return new SnakePit(this);
    }
}
