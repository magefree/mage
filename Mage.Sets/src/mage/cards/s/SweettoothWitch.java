package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SweettoothWitch extends CardImpl {

    public SweettoothWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Sweettooth Witch enters the battlefield, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // {2}, Sacrifice a Food: Target player loses 2 life.
        Ability ability = new SimpleActivatedAbility(new LoseLifeTargetEffect(2), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_FOOD));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private SweettoothWitch(final SweettoothWitch card) {
        super(card);
    }

    @Override
    public SweettoothWitch copy() {
        return new SweettoothWitch(this);
    }
}
