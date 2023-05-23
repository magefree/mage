package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeirloomMirror extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCreatureCard("creature card from a graveyard");

    public HeirloomMirror(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{B}",
                "Inherited Fiend",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DEMON}, "B"
        );
        this.getRightHalfCard().setPT(4, 4);

        // {1}, {T}, Pay 1 life, Discard a card: Draw a card, mill a card, then put a ritual counter on Heirloom Mirror. Then if it has 3 or more ritual counters on it, remove them and transform it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new HeirloomMirrorEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        ability.addCost(new DiscardCardCost());
        this.getLeftHalfCard().addAbility(ability);

        // Inherited Fiend
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // {2}{B}: Exile target creature card from a graveyard. Put a +1/+1 counter on Inherited Fiend.
        ability = new SimpleActivatedAbility(new ExileTargetEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("."));
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.getRightHalfCard().addAbility(ability);
    }

    private HeirloomMirror(final HeirloomMirror card) {
        super(card);
    }

    @Override
    public HeirloomMirror copy() {
        return new HeirloomMirror(this);
    }
}

class HeirloomMirrorEffect extends OneShotEffect {

    HeirloomMirrorEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card, mill a card, then put a ritual counter on {this}. " +
                "Then if it has three or more ritual counters on it, remove them and transform it";
    }

    private HeirloomMirrorEffect(final HeirloomMirrorEffect effect) {
        super(effect);
    }

    @Override
    public HeirloomMirrorEffect copy() {
        return new HeirloomMirrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        player.drawCards(1, source, game);
        player.millCards(1, source, game);
        permanent.addCounters(CounterType.RITUAL.createInstance(), source.getControllerId(), source, game);
        int counters = permanent.getCounters(game).getCount(CounterType.RITUAL);
        if (counters < 3) {
            return true;
        }
        permanent.removeCounters(CounterType.RITUAL.createInstance(counters), source, game);
        new TransformSourceEffect().apply(game, source);
        return true;
    }
}
