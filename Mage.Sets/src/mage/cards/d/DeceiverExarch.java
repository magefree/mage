
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public final class DeceiverExarch extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public DeceiverExarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        this.addAbility(FlashAbility.getInstance());
        // When Deceiver Exarch enters the battlefield, choose one - Untap target permanent you control; or tap target permanent an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new UntapTargetEffect());
        ability.addTarget(new TargetControlledPermanent());
        Mode mode = new Mode(new TapTargetEffect());
        mode.addTarget(new TargetPermanent(filter));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private DeceiverExarch(final DeceiverExarch card) {
        super(card);
    }

    @Override
    public DeceiverExarch copy() {
        return new DeceiverExarch(this);
    }
}
