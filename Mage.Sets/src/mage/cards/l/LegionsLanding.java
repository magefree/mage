
package mage.cards.l;

import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.game.permanent.token.IxalanVampireToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LegionsLanding extends CardImpl {

    public LegionsLanding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.a.AdantoTheFirstFort.class;

        // When Legion's Landing enters the battlefield, create a 1/1 white Vampire creature token with lifelink.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new IxalanVampireToken())));

        // When you attack with three or more creatures, transform Legion's Landing.
        this.addAbility(new TransformAbility());
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new TransformSourceEffect(), 3).setTriggerPhrase("When you attack with three or more creatures, "));
    }

    private LegionsLanding(final LegionsLanding card) {
        super(card);
    }

    @Override
    public LegionsLanding copy() {
        return new LegionsLanding(this);
    }
}
