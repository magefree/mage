package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class SinisterConcierge extends CardImpl {

    public SinisterConcierge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.addSubType(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Sinister Concierge dies, you may exile it and put three time counters on it.
        // If you do, exile up to one target creature and put three time counters on it.
        // Each card exiled this way that doesn't have suspend gains suspend.
        // (For each card with suspend, its owner removes a time counter from it at the beginning of their upkeep.
        // When the last is removed, they may cast it without paying its mana cost. Those creature spells have haste.)
        Ability ability = new DiesSourceTriggeredAbility(new SinisterConciergeEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private SinisterConcierge(final SinisterConcierge card) {
        super(card);
    }

    @Override
    public SinisterConcierge copy() {
        return new SinisterConcierge(this);
    }
}

class SinisterConciergeEffect extends OneShotEffect {
    public SinisterConciergeEffect() {
        super(Outcome.Removal);
        this.staticText = "exile it and put three time counters on it. If you do, exile up to one target creature " +
                "and put three time counters on it. Each card exiled this way that doesn't have suspend gains suspend. " +
                "<i>(For each card with suspend, its owner removes a time counter from it at the beginning of their upkeep. " +
                "When the last is removed, they may cast it without paying its mana cost. Those creature spells have haste.)</i>";
    }

    private SinisterConciergeEffect(final SinisterConciergeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (controller == null || card == null
                || card.getZoneChangeCounter(game) != source.getStackMomentSourceZCC()
                || !Zone.GRAVEYARD.match(game.getState().getZone(card.getId()))) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        SuspendAbility.addTimeCountersAndSuspend(card, 3, source, game);
        Permanent targetCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            controller.moveCards(targetCreature, Zone.EXILED, source, game);
            SuspendAbility.addTimeCountersAndSuspend(targetCreature.getMainCard(), 3, source, game);
        }
        return true;
    }

    @Override
    public SinisterConciergeEffect copy() {
        return new SinisterConciergeEffect(this);
    }
}
