
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public final class MoldShambler extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public MoldShambler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kicker {1}{G} (You may pay an additional {1}{G} as you cast this spell.)
        this.addAbility(new KickerAbility("{1}{G}"));

        // When Mold Shambler enters the battlefield, if it was kicked, destroy target noncreature permanent.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        Target target = new TargetPermanent(filter);
        ability.addTarget(target);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, destroy target noncreature permanent."));
    }

    private MoldShambler(final MoldShambler card) {
        super(card);
    }

    @Override
    public MoldShambler copy() {
        return new MoldShambler(this);
    }
}
