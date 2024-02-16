
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class OranRiefRecluse extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public OranRiefRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Kicker {2}{G} (You may pay an additional {2}{G} as you cast this spell.)
        this.addAbility(new KickerAbility("{2}{G}"));

        // Reach (This creature can block creatures with flying.)
        this.addAbility(ReachAbility.getInstance());

        // When Oran-Rief Recluse enters the battlefield, if it was kicked, destroy target creature with flying.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, destroy target creature with flying."));
    }

    private OranRiefRecluse(final OranRiefRecluse card) {
        super(card);
    }

    @Override
    public OranRiefRecluse copy() {
        return new OranRiefRecluse(this);
    }
}
