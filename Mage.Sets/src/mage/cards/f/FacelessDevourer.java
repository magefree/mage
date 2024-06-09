package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
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
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class FacelessDevourer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature with shadow");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new AbilityPredicate(ShadowAbility.class));
    }

    public FacelessDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());

        // When Faceless Devourer enters the battlefield, exile another target creature with shadow.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // When Faceless Devourer leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        ability = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false);
        this.addAbility(ability);
    }

    private FacelessDevourer(final FacelessDevourer card) {
        super(card);
    }

    @Override
    public FacelessDevourer copy() {
        return new FacelessDevourer(this);
    }
}
