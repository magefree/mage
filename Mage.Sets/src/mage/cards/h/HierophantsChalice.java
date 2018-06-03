
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class HierophantsChalice extends CardImpl {

    public HierophantsChalice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // When Hierophant's Chalice enters the battlefield, target opponent loses 1 life and you gain 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(1), false);
        ability.addEffect(new GainLifeEffect(1).setText("and you gain one life."));
        Target target = new TargetOpponent();
        ability.addTarget(target);
        this.addAbility(ability);

        // {t}: Add {c}.
        this.addAbility(new ColorlessManaAbility());
    }

    public HierophantsChalice(final HierophantsChalice card) {
        super(card);
    }

    @Override
    public HierophantsChalice copy() {
        return new HierophantsChalice(this);
    }
}
