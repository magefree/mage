
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class JolraelEmpressOfBeasts extends CardImpl {

    public JolraelEmpressOfBeasts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{G}, {tap}, Discard two cards: All lands target player controls become 3/3 creatures until end of turn. They're still lands.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JolraelEmpressOfBeastsEffect(), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private JolraelEmpressOfBeasts(final JolraelEmpressOfBeasts card) {
        super(card);
    }

    @Override
    public JolraelEmpressOfBeasts copy() {
        return new JolraelEmpressOfBeasts(this);
    }
}

class JolraelEmpressOfBeastsEffect extends OneShotEffect {

    public JolraelEmpressOfBeastsEffect() {
        super(Outcome.Benefit);
        this.staticText = "All lands target player controls become 3/3 creatures until end of turn. They're still lands.";
    }

    public JolraelEmpressOfBeastsEffect(final JolraelEmpressOfBeastsEffect effect) {
        super(effect);
    }

    @Override
    public JolraelEmpressOfBeastsEffect copy() {
        return new JolraelEmpressOfBeastsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            FilterPermanent filter = new FilterLandPermanent();
            filter.add(new ControllerIdPredicate(targetPlayer.getId()));
            game.addEffect(new BecomesCreatureAllEffect(new CreatureToken(3, 3), "lands", filter, Duration.EndOfTurn, false), source);
            return true;
        }
        return false;
    }
}