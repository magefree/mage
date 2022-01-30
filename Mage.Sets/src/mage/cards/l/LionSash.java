package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.ReconfigureAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LionSash extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.P1P1);

    public LionSash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {W}: Exile target card from a graveyard. If it was a permanent card, put a +1/+1 counter on Lion Sash.
        Ability ability = new SimpleActivatedAbility(new LionSashEffect(), new ManaCostsImpl<>("{W}"));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // Equipped creature gets +1/+1 for each +1/+1 counter on Lion Sash.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(xValue, xValue)));

        // Reconfigure {2}
        this.addAbility(new ReconfigureAbility("{2}"));
    }

    private LionSash(final LionSash card) {
        super(card);
    }

    @Override
    public LionSash copy() {
        return new LionSash(this);
    }
}

class LionSashEffect extends OneShotEffect {

    LionSashEffect() {
        super(Outcome.Benefit);
        staticText = "exile target card from a graveyard. If it was a permanent card, put a +1/+1 counter on {this}";
    }

    private LionSashEffect(final LionSashEffect effect) {
        super(effect);
    }

    @Override
    public LionSashEffect copy() {
        return new LionSashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (card.isPermanent(game) && permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        }
        return true;
    }
}
