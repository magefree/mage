
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class AdmonitionAngel extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nonland permanent other than Admonition Angel");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public AdmonitionAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Landfall - Whenever a land enters the battlefield under your control, you may exile target nonland permanent other than Admonition Angel.
        TriggeredAbility ability = new LandfallAbility(Zone.BATTLEFIELD, new ExileTargetForSourceEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // When Admonition Angel leaves the battlefield, return all cards exiled with it to the battlefield under their owners' control.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD)
                .setText("return all cards exiled with it to the battlefield under their owners' control"), false);
        this.addAbility(ability2);
    }

    private AdmonitionAngel(final AdmonitionAngel card) {
        super(card);
    }

    @Override
    public AdmonitionAngel copy() {
        return new AdmonitionAngel(this);
    }
}
