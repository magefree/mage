package mage.cards.l;

import java.util.UUID;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author jeffwadsworth
 */
public final class LeechriddenSwamp extends CardImpl {

    private static final FilterControlledPermanent filter = 
            new FilterControlledPermanent("you control two or more black permanents");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public LeechriddenSwamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.subtype.add(SubType.SWAMP);

        // ({tap}: Add {B}.)
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new TapSourceCost()));

        // Leechridden Swamp enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {B}, {tap}: Each opponent loses 1 life. Activate this ability only if you control two or more black permanents.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new LeechriddenSwampLoseLifeEffect(),
                new ManaCostsImpl<>("{B}"),
                new PermanentsOnTheBattlefieldCondition(
                        filter, 
                        ComparisonType.MORE_THAN, 
                        1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private LeechriddenSwamp(final LeechriddenSwamp card) {
        super(card);
    }

    @Override
    public LeechriddenSwamp copy() {
        return new LeechriddenSwamp(this);
    }
}

class LeechriddenSwampLoseLifeEffect extends OneShotEffect {

    LeechriddenSwampLoseLifeEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent loses 1 life";
    }

    LeechriddenSwampLoseLifeEffect(LeechriddenSwampLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID opponentId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    opponent.loseLife(1, game, source, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public LeechriddenSwampLoseLifeEffect copy() {
        return new LeechriddenSwampLoseLifeEffect(this);
    }
}
