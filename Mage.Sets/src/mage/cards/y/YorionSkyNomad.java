package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YorionSkyNomad extends CardImpl {

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent("other nonland permanents you own and control");
    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(AnotherPredicate.instance);
    }
    private static final String ruleText = "exile any number of other nonland permanents you own and control. " +
            "Return those cards to the battlefield at the beginning of the next end step.";

    public YorionSkyNomad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] {CardType.CREATURE}, "{3}{W/U}{W/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Companion — Your starting deck contains at least twenty cards more than the minimum deck size.
        this.addAbility(new CompanionAbility(YorionSkyNomadCompanionCondition.instance));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Yorion enters the battlefield, exile any number of other nonland permanents you own
        // and control. Return those cards to the battlefield at the beginning of the next end step.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ExileReturnBattlefieldNextEndStepTargetEffect().setText(ruleText));
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter, true));
        this.addAbility(ability);
    }

    private YorionSkyNomad(final YorionSkyNomad card) {
        super(card);
    }

    @Override
    public YorionSkyNomad copy() {
        return new YorionSkyNomad(this);
    }
}

enum YorionSkyNomadCompanionCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "Your starting deck contains at least twenty cards more than the minimum deck size.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int minimumDeckSize) {
        return deck.size() >= minimumDeckSize + 20;
    }
}
