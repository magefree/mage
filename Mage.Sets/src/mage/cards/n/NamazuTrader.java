package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NamazuTrader extends CardImpl {

    public NamazuTrader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When this creature enters, you lose 1 life and create a Treasure token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeSourceControllerEffect(1));
        ability.addEffect(new CreateTokenEffect(new TreasureToken()).concatBy("and"));
        this.addAbility(ability);

        // Whenever this creature attacks, you may sacrifice another creature or artifact. If you do, surveil 2.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new SurveilEffect(2),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT)
        )));
    }

    private NamazuTrader(final NamazuTrader card) {
        super(card);
    }

    @Override
    public NamazuTrader copy() {
        return new NamazuTrader(this);
    }
}
