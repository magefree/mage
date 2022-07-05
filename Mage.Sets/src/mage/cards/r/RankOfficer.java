package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RankOfficer extends CardImpl {

    public RankOfficer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Rank Officer enters the battlefield, you may discard a card. If you do, create a 2/2 black Zombie creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new CreateTokenEffect(new ZombieToken()), new DiscardCardCost())
        ));

        // {1}{B}, {T}, Exile a creature card from your graveyard: Each opponent loses 2 life.
        Ability ability = new SimpleActivatedAbility(
                new LoseLifeOpponentsEffect(2), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
        )));
        this.addAbility(ability);
    }

    private RankOfficer(final RankOfficer card) {
        super(card);
    }

    @Override
    public RankOfficer copy() {
        return new RankOfficer(this);
    }
}
