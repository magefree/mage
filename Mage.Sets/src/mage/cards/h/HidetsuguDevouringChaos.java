package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HidetsuguDevouringChaos extends CardImpl {

    public HidetsuguDevouringChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {B}, Sacrifice a creature: Scry 2.
        Ability ability = new SimpleActivatedAbility(new ScryEffect(2), new ManaCostsImpl<>("{B}"));
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ));
        this.addAbility(ability);

        // {2}{R}, {T}: Exile the top card of your library. You may play that card this turn. When you exile a nonland card this way, Hidetsugu, Devouring Chaos deals damage equal to the exiled card's mana value to any target.
        ability = new SimpleActivatedAbility(new HidetsuguDevouringChaosEffect(), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private HidetsuguDevouringChaos(final HidetsuguDevouringChaos card) {
        super(card);
    }

    @Override
    public HidetsuguDevouringChaos copy() {
        return new HidetsuguDevouringChaos(this);
    }
}

class HidetsuguDevouringChaosEffect extends OneShotEffect {

    HidetsuguDevouringChaosEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. You may play that card this turn. " +
                "When you exile a nonland card this way, {this} deals damage equal " +
                "to the exiled card's mana value to any target";
    }

    private HidetsuguDevouringChaosEffect(final HidetsuguDevouringChaosEffect effect) {
        super(effect);
    }

    @Override
    public HidetsuguDevouringChaosEffect copy() {
        return new HidetsuguDevouringChaosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(
                game, source, card, TargetController.YOU, Duration.EndOfTurn,
                false, false, false
        );
        if (card.isLand(game)) {
            return true;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(card.getManaValue()), false
        );
        ability.addTarget(new TargetAnyTarget());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
