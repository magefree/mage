package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.BargainAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TroublemakerOuphe extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or enchantment an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public TroublemakerOuphe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.OUPHE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bargain (You may sacrifice an artifact, enchantment, or token as you cast this spell.)
        this.addAbility(new BargainAbility());

        // When Troublemaker Ouphe enters the battlefield, if it was bargained, exile target artifact or enchantment an opponent controls.
        TriggeredAbility trigger = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        trigger.addTarget(new TargetPermanent(filter));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                trigger,
                BargainedCondition.instance,
                "When {this} enters the battlefield, if it was bargained, exile target artifact or enchantment an opponent controls."
        ));
    }

    private TroublemakerOuphe(final TroublemakerOuphe card) {
        super(card);
    }

    @Override
    public TroublemakerOuphe copy() {
        return new TroublemakerOuphe(this);
    }
}
