package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TwoOfManaColorSpentCondition;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Wistfulness extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public Wistfulness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G/U}{G/U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // When this creature enters, if {G}{G} was spent to cast it, exile target artifact or enchantment an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect())
                .withInterveningIf(TwoOfManaColorSpentCondition.GREEN);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // When this creature enters, if {U}{U} was spent to cast it, draw two cards, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawDiscardControllerEffect(2, 1)
        ).withInterveningIf(TwoOfManaColorSpentCondition.BLUE));

        // Evoke {G/U}{G/U}
        this.addAbility(new EvokeAbility("{G/U}{G/U}"));
    }

    private Wistfulness(final Wistfulness card) {
        super(card);
    }

    @Override
    public Wistfulness copy() {
        return new Wistfulness(this);
    }
}
