package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ControllerGotLifeCount;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RodolfDuskbringer extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card with mana value less than or equal to the amount of life you gained this turn"
    );

    static {
        filter.add(RodolfDuskbringerPredicate.instance);
    }

    public RodolfDuskbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you gain life, Rodolf Duskbringer gains indestructible until end of turn.
        this.addAbility(new GainLifeControllerTriggeredAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        )));

        // At the beginning of your end step, you may pay {1}{W/B}. When you do, return target creature card with mana value X or less from your graveyard to the battlefield, where X is the amount of life you gained this turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                false, "return target creature card with mana value X or less from " +
                "your graveyard to the battlefield, where X is the amount of life you gained this turn"
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DoWhenCostPaid(
                ability, new ManaCostsImpl<>("{1}{W/B}"), "Pay {1}{W/B}?"
        ), TargetController.YOU, false).addHint(ControllerGotLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private RodolfDuskbringer(final RodolfDuskbringer card) {
        super(card);
    }

    @Override
    public RodolfDuskbringer copy() {
        return new RodolfDuskbringer(this);
    }
}

enum RodolfDuskbringerPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        return game
                .getState()
                .getWatcher(PlayerGainedLifeWatcher.class)
                .getLifeGained(input.getOwnerId())
                <= input.getManaValue();
    }
}