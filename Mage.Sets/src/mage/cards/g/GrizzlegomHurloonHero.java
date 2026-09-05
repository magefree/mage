package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.RulebreakerAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandCard;
import mage.game.permanent.token.SoldierToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GrizzlegomHurloonHero extends CardImpl {

    private static final FilterControlledPermanent filterPlains = new FilterControlledPermanent(SubType.PLAINS, "Plains you control");
    private static final FilterControlledPermanent filterIslands = new FilterControlledPermanent(SubType.ISLAND, "Island you control");
    private static final FilterControlledPermanent filterSwamps = new FilterControlledPermanent(SubType.SWAMP, "Swamp you control");
    private static final FilterControlledPermanent filterMountains = new FilterControlledPermanent(SubType.MOUNTAIN, "Mountain you control");
    private static final FilterControlledPermanent filterForests = new FilterControlledPermanent(SubType.FOREST, "Forest you control");

    private static final PermanentsOnBattlefieldCount countPlains = new PermanentsOnBattlefieldCount(filterPlains);
    private static final PermanentsOnBattlefieldCount countIslands = new PermanentsOnBattlefieldCount(filterIslands);
    private static final PermanentsOnBattlefieldCount countSwamps = new PermanentsOnBattlefieldCount(filterSwamps);
    private static final PermanentsOnBattlefieldCount countMountains = new PermanentsOnBattlefieldCount(filterMountains);
    private static final PermanentsOnBattlefieldCount countForests = new PermanentsOnBattlefieldCount(filterForests);

    private static final Hint plainsHint = new ValueHint("Plains you control", countPlains);
    private static final Hint islandsHint = new ValueHint("Islands you control", countIslands);
    private static final Hint swampsHint = new ValueHint("Swamps you control", countSwamps);
    private static final Hint mountainsHint = new ValueHint("Mountains you control", countMountains);
    private static final Hint forestsHint = new ValueHint("Forests you control", countForests);

    public GrizzlegomHurloonHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Rulebreaker -- A deck with this commander can have any land cards.
        this.addAbility(new RulebreakerAbility(new FilterLandCard("can have any land cards"), false, false));

        // Whenever Grizzlegom attacks, create a 1/1 white Soldier creature token for each Plains you control. Draw a card for each Island you control. Each opponent loses 1 life for each Swamp you control. Put a +1/+1 counter on Grizzlegom for each Mountain you control. You gain 1 life for each Forest you control.
        Ability ability = new AttacksTriggeredAbility(new CreateTokenEffect(new SoldierToken(), countPlains)
            .setText("create a 1/1 white Soldier creature token for each Plains you control."));
        ability.addEffect(new DrawCardSourceControllerEffect(countIslands)
            .setText("Draw a card for each Island you control."));
        ability.addEffect(new LoseLifeOpponentsEffect(countSwamps)
            .setText("Each opponent loses 1 life for each Swamp you control."));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), countMountains)
            .setText("Put a +1/+1 counter on {this} for each Mountain you control."));
        ability.addEffect(new GainLifeEffect(countForests)
            .setText("You gain 1 life for each Forest you control."));
        this.addAbility(ability
            .addHint(plainsHint)
            .addHint(islandsHint)
            .addHint(swampsHint)
            .addHint(mountainsHint)
            .addHint(forestsHint)
        );
    }

    private GrizzlegomHurloonHero(final GrizzlegomHurloonHero card) {
        super(card);
    }

    @Override
    public GrizzlegomHurloonHero copy() {
        return new GrizzlegomHurloonHero(this);
    }
}
