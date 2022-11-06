package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.DrawSecondCardTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class GixianPuppeteer extends CardImpl {

    private static final FilterCreatureCard filter
            = new FilterCreatureCard("another target creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public GixianPuppeteer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever you draw your second card each turn, each opponent loses 2 life and you gain 2 life.
        Ability ability = new DrawSecondCardTriggeredAbility(new LoseLifeOpponentsEffect(2), false);
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);

        // When Gixian Puppeteer dies, return another target creature card with mana value 3 or less from your graveyard to the battlefield.
        ability = new DiesSourceTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private GixianPuppeteer(final GixianPuppeteer card) {
        super(card);
    }

    @Override
    public GixianPuppeteer copy() {
        return new GixianPuppeteer(this);
    }
}
