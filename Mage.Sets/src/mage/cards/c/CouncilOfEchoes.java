package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CouncilOfEchoes extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent other than {this}");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public CouncilOfEchoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Descend 4 -- When Council of Echoes enters the battlefield, if there are four or more permanent cards in your graveyard, return up to one target nonland permanent other than Council of Echoes to its owner's hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false),
                DescendCondition.FOUR, "When {this} enters the battlefield, "
                + "if there are four or more permanent cards in your graveyard, "
                + "return up to one target nonland permanent other than {this} to its owner's hand"
        );
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability.addHint(DescendCondition.getHint()).setAbilityWord(AbilityWord.DESCEND_4));
    }

    private CouncilOfEchoes(final CouncilOfEchoes card) {
        super(card);
    }

    @Override
    public CouncilOfEchoes copy() {
        return new CouncilOfEchoes(this);
    }
}
