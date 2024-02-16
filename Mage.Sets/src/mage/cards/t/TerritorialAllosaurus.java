
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TerritorialAllosaurus extends CardImpl {

    public TerritorialAllosaurus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Kicker {2}{G}
        this.addAbility(new KickerAbility("{2}{G}"));

        // When Territorial Allosaurus enters the battlefield, if it was kicked, it fights another target creature.
        EntersBattlefieldTriggeredAbility ability
                = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect());
        Ability conditionalAbility = new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE,
                "When {this} enters the battlefield, if it was kicked, it fights another target creature.");
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(AnotherPredicate.instance);
        conditionalAbility.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(conditionalAbility);
    }

    private TerritorialAllosaurus(final TerritorialAllosaurus card) {
        super(card);
    }

    @Override
    public TerritorialAllosaurus copy() {
        return new TerritorialAllosaurus(this);
    }
}
