package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.OmenCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class FeralDeathgorger extends OmenCard {

    public FeralDeathgorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{5}{B}",
                "Dusk Sight",
                new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Feral Deathgorger
        this.getLeftHalfCard().setPT(3, 5);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.getLeftHalfCard().addAbility(DeathtouchAbility.getInstance());

        // When this creature enters, exile up to two target cards from a single graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInASingleGraveyard(0, 2, StaticFilters.FILTER_CARD_CARDS));
        this.getLeftHalfCard().addAbility(ability);

        // Dusk Sight
        // Put a +1/+1 counter on up to one target creature. Draw a card.
        this.getRightHalfCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(), StaticValue.get(1)));
        this.getRightHalfCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));

        finalizeCard();
    }

    private FeralDeathgorger(final FeralDeathgorger card) {
        super(card);
    }

    @Override
    public FeralDeathgorger copy() {
        return new FeralDeathgorger(this);
    }
}
