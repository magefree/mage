
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileReturnBattlefieldOwnerNextEndStepSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class HikariTwilightGuardian extends CardImpl {

    public HikariTwilightGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a Spirit or Arcane spell, you may exile Hikari, Twilight Guardian. If you do, return it to the battlefield under its owner's control at the beginning of the next end step.
        Effect effect = new ExileReturnBattlefieldOwnerNextEndStepSourceEffect();
        effect.setText("you may exile {this}. If you do, return it to the battlefield under its owner's control at the beginning of the next end step");
        this.addAbility(new SpellCastControllerTriggeredAbility(effect, StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));
    }

    private HikariTwilightGuardian(final HikariTwilightGuardian card) {
        super(card);
    }

    @Override
    public HikariTwilightGuardian copy() {
        return new HikariTwilightGuardian(this);
    }

}
