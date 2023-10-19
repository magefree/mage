package mage.cards.t;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.GainSuspendEffect;
import mage.abilities.effects.common.counter.TimeTravelEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TheTenthDoctor extends CardImpl {

    public TheTenthDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD, SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Allons-y! — Whenever you attack, exile cards from the top of your library until you exile a nonland card. Put three time counters on it. If it doesn't have suspend, it gains suspend.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new TheTenthDoctorEffect(), 1).withFlavorWord("Allons-y!"));

        // Timey-Wimey — {7}: Time travel three times. Activate only as a sorcery. (For each suspended card you own and each permanent you control with a time counter on it, you may add or remove a time counter. Then do it two more times.)
        Ability ability = new ActivateAsSorceryActivatedAbility(new TimeTravelEffect().setText("Time travel three times"), new GenericManaCost(7));
        ability.addEffect(new TimeTravelEffect().setText(""));
        ability.addEffect(new TimeTravelEffect().setText(""));
        ability.addEffect(new InfoEffect("<i>(For each suspended card you own and each permanent you control with a time counter on it, you may add or remove a time counter. Then do it two more times.)</i>"));
        this.addAbility(ability.withFlavorWord("Timey-Wimey"));
    }

    private TheTenthDoctor(final TheTenthDoctor card) {
        super(card);
    }

    @Override
    public TheTenthDoctor copy() {
        return new TheTenthDoctor(this);
    }
}

class TheTenthDoctorEffect extends OneShotEffect {

    public TheTenthDoctorEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile a nonland card. " +
                "Put three time counters on it. If it doesn't have suspend, it gains suspend";
    }

    private TheTenthDoctorEffect(final TheTenthDoctorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (Card card : controller.getLibrary().getCards(game)) {
            if (!card.isLand(game)) {
                boolean hasSuspend = card.getAbilities(game).containsClass(SuspendAbility.class);
                UUID exileId = SuspendAbility.getSuspendExileId(controller.getId(), game);
                if (controller.moveCardToExileWithInfo(card, exileId, "Suspended cards of " + controller.getName(), source, game, Zone.LIBRARY, true)) {
                    card.addCounters(CounterType.TIME.createInstance(3), source.getControllerId(), source, game);
                    if (!hasSuspend) {
                        game.addEffect(new GainSuspendEffect(new MageObjectReference(card, game)), source);
                    }
                    game.informPlayers(controller.getLogName() + " suspends 3 - " + card.getName());
                }
                break;
            }
            controller.moveCards(card, Zone.EXILED, source, game);
        }
        return true;
    }

    @Override
    public TheTenthDoctorEffect copy() {
        return new TheTenthDoctorEffect(this);
    }
}
