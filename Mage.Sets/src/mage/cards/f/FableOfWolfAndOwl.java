
package mage.cards.f;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.BlueBirdToken;
import mage.game.permanent.token.WolfToken;

/**
 * @author Loki
 */
public final class FableOfWolfAndOwl extends CardImpl {

    private static final FilterSpell filterGreenSpell = new FilterSpell("a green spell");
    private static final FilterSpell filterBlueSpell = new FilterSpell("a blue spell");

    static {
        filterGreenSpell.add(new ColorPredicate(ObjectColor.GREEN));
        filterBlueSpell.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public FableOfWolfAndOwl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G/U}{G/U}{G/U}");

        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new WolfToken(), 1), filterGreenSpell, true));
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new BlueBirdToken(), 1), filterBlueSpell, true));
    }

    private FableOfWolfAndOwl(final FableOfWolfAndOwl card) {
        super(card);
    }

    @Override
    public FableOfWolfAndOwl copy() {
        return new FableOfWolfAndOwl(this);
    }
}
