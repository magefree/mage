package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.*;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author weirddan455
 */
public final class DragonsDisciple extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "Dragons");

    public DragonsDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // As Dragon's Disciple enters the battlefield, you may reveal a Dragon card from your hand. If you do, or if you control a Dragon, Dragon's Disciple enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new AsEntersBattlefieldAbility(new DragonsDiscipleEffect()));

        // Dragons you control have ward {1}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(1)), Duration.WhileOnBattlefield, filter
        ).setText("Dragons you control have ward {1}. " +
                "<i>(Whenever a Dragon you control becomes the target of a spell or ability an opponent controls, " +
                "counter it unless that player pays 1.)</i>")));
    }

    private DragonsDisciple(final DragonsDisciple card) {
        super(card);
    }

    @Override
    public DragonsDisciple copy() {
        return new DragonsDisciple(this);
    }
}

class DragonsDiscipleEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Dragon card from your hand");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public DragonsDiscipleEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may reveal a Dragon card from your hand. If you do or if you control a Dragon, {this} enters the battlefield with a +1/+1 counter on it";
    }

    private DragonsDiscipleEffect(final DragonsDiscipleEffect effect) {
        super(effect);
    }

    @Override
    public DragonsDiscipleEffect copy() {
        return new DragonsDiscipleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentEntering(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            TargetCardInHand target = new TargetCardInHand(filter);
            if (!target.possibleTargets(source.getSourceId(), source.getControllerId(), game).isEmpty()
                    && controller.chooseUse(outcome, "Reveal a Dragon card from your hand?", source, game)
                    && controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    Cards revealedCards = new CardsImpl(card);
                    controller.revealCards(source, sourcePermanent.getIdName(), revealedCards, game);
                    sourcePermanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                    return true;
                }
            }
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
                if (permanent != null && permanent.hasSubtype(SubType.DRAGON, game)) {
                    sourcePermanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                    return true;
                }
            }
        }
        return false;
    }
}
