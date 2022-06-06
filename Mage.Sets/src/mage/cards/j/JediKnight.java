
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.MeditateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author Styxo
 */
public final class JediKnight extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public JediKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.JEDI);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Jedi Knight leaves the battlefield, return target nonland permanent you don't control to its owner's hands.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetNonlandPermanent(filter));
        this.addAbility(ability);

        // Meditate {1}{U}
        this.addAbility(new MeditateAbility(new ManaCostsImpl<>("{1}{U}")));
    }

    private JediKnight(final JediKnight card) {
        super(card);
    }

    @Override
    public JediKnight copy() {
        return new JediKnight(this);
    }
}
