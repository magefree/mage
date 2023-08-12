package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BogardanDragonheart extends CardImpl {

    public BogardanDragonheart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice another creature: Until end of turn, Bogardan Dragonheart becomes a Dragon with base power and toughness 4/4, flying, and haste.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new BogardanDragonheartToken(), CardType.CREATURE, Duration.EndOfTurn
        ), new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE))));
    }

    private BogardanDragonheart(final BogardanDragonheart card) {
        super(card);
    }

    @Override
    public BogardanDragonheart copy() {
        return new BogardanDragonheart(this);
    }
}

class BogardanDragonheartToken extends TokenImpl {

    BogardanDragonheartToken() {
        super("", "Dragon with base power and toughness 4/4, flying, and haste");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DRAGON);
        power = new MageInt(4);
        toughness = new MageInt(4);

        addAbility(FlyingAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    private BogardanDragonheartToken(final BogardanDragonheartToken token) {
        super(token);
    }

    public BogardanDragonheartToken copy() {
        return new BogardanDragonheartToken(this);
    }
}
