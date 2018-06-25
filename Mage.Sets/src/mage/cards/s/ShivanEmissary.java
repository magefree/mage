
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox

 */
public final class ShivanEmissary extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public ShivanEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kicker {1}{B}
        this.addAbility(new KickerAbility("{1}{B}"));
        // When Shivan Emissary enters the battlefield, if it was kicked, destroy target nonblack creature. It can't be regenerated.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(true));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.instance,
            "When {this} enters the battlefield, if it was kicked, destroy target nonblack creature. It can't be regenerated."));
    }

    public ShivanEmissary(final ShivanEmissary card) {
        super(card);
    }

    @Override
    public ShivanEmissary copy() {
        return new ShivanEmissary(this);
    }
}
