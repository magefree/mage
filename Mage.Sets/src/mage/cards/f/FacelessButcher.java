
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.Target;
import mage.target.TargetPermanent;


/**
 *
 * @author Temba21
 */
public final class FacelessButcher extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();


    static {
         filter.add(new AnotherPredicate());
    }


    public FacelessButcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);


        // When Faceless Butcher enters the battlefield, exile target creature other than Faceless Butcher.
        Effect effect = new ExileTargetForSourceEffect();
        effect.setText("exile target creature other than {this}");
        Ability ability1 = new EntersBattlefieldTriggeredAbility(effect, false);
        Target target = new TargetPermanent(filter);
        ability1.addTarget(target);
        this.addAbility(ability1);

        // When Faceless Butcher leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false);
        this.addAbility(ability2);

    }

    public FacelessButcher(final FacelessButcher card) {
        super(card);
    }

    @Override
    public FacelessButcher copy() {
        return new FacelessButcher(this);
    }
}
