
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;


/**
 *
 * @author noxx
 */
public final class CaptainOfTheMists extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another Human");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.HUMAN.getPredicate());
    }

    public CaptainOfTheMists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another Human enters the battlefield under your control, untap Captain of the Mists.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new UntapSourceEffect(), filter));

        // {1}{U}, {tap}: You may tap or untap target permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MayTapOrUntapTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private CaptainOfTheMists(final CaptainOfTheMists card) {
        super(card);
    }

    @Override
    public CaptainOfTheMists copy() {
        return new CaptainOfTheMists(this);
    }
}
