
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox
 */
public final class FacelessDevourer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new AbilityPredicate(ShadowAbility.class));
    }

    public FacelessDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // When Faceless Devourer enters the battlefield, exile another target creature with shadow.
        Effect effect = new ExileTargetForSourceEffect();
        effect.setText("exile another target creature with shadow");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        Target target = new TargetPermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);
        // When Faceless Devourer leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        ability = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false);
        this.addAbility(ability);
    }

    public FacelessDevourer(final FacelessDevourer card) {
        super(card);
    }

    @Override
    public FacelessDevourer copy() {
        return new FacelessDevourer(this);
    }
}
