package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessTargetEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Susucr
 */
public final class BillThePony extends CardImpl {

    public BillThePony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Bill the Pony enters the battlefield, create two Food tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken(), 2)));

        // Sacrifice a Food: Until end of turn, target creature you control assigns combat damage equal to its toughness rather than its power.
        Ability ability = new SimpleActivatedAbility(
            new CombatDamageByToughnessTargetEffect(Duration.EndOfTurn),
            new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_FOOD))
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE));

        this.addAbility(ability);
    }

    private BillThePony(final BillThePony card) {
        super(card);
    }

    @Override
    public BillThePony copy() {
        return new BillThePony(this);
    }
}