
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class SlitheryStalker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("green or white creature an opponent controls");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.GREEN),
                new ColorPredicate(ObjectColor.WHITE)));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SlitheryStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());

        // When Slithery Stalker enters the battlefield, exile target green or white creature an opponent controls.
        Ability ability1 = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect());
        Target target = new TargetPermanent(filter);
        ability1.addTarget(target);
        this.addAbility(ability1);

        // When Slithery Stalker leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false);
        this.addAbility(ability2);
    }

    private SlitheryStalker(final SlitheryStalker card) {
        super(card);
    }

    @Override
    public SlitheryStalker copy() {
        return new SlitheryStalker(this);
    }
}
