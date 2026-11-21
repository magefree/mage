package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

import java.util.UUID;

public final class MoleculeMan extends CardImpl {

    public MoleculeMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.VILLAIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        Ability ability = new MoleculeManMiracleTriggeredAbility();
        this.addAbility(ability);
    }

    private MoleculeMan(final MoleculeMan card) {
        super(card);
    }

    @Override
    public MoleculeMan copy() {
        return new MoleculeMan(this);
    }
}

class MoleculeManMiracleTriggeredAbility extends TriggeredAbilityImpl {

    MoleculeManMiracleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MoleculeManMiracleEffect(), false);
        setTriggerPhrase("Whenever you draw your first card each turn, if it's a nonland card, "
                + "you may cast it for its miracle cost of {0}");
    }

    private MoleculeManMiracleTriggeredAbility(final MoleculeManMiracleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!getControllerId().equals(event.getPlayerId())) {
            return false;
        }
        CardsDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsDrawnThisTurnWatcher.class);
        Card card = game.getCard(event.getTargetId());
        if (watcher == null || watcher.getCardsDrawnThisTurn(getControllerId()) != 1
                || card == null || card.isLand(game)) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(card.getId(), game));
        return true;
    }

    @Override
    public MoleculeManMiracleTriggeredAbility copy() {
        return new MoleculeManMiracleTriggeredAbility(this);
    }
}

class MoleculeManMiracleEffect extends OneShotEffect {

    MoleculeManMiracleEffect() {
        super(Outcome.PlayForFree);
        staticText = "you may cast that card for its miracle cost of {0}";
    }

    private MoleculeManMiracleEffect(final MoleculeManMiracleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null
                || game.getState().getZone(card.getId()) != Zone.HAND
                || !controller.chooseUse(outcome, "Cast this card for its miracle cost of {0}?", source, game)) {
            return false;
        }
        Cards cards = new CardsImpl(card);
        controller.revealCards("Miracle", cards, game);
        SpellAbility ability = card.getSpellAbility().copy();
        ability.getManaCostsToPay().clear();
        ability.getManaCostsToPay().add(new mage.abilities.costs.mana.ManaCostsImpl<>("{0}"));
        return controller.cast(ability, game, false, new mage.ApprovingObject(source, game));
    }

    @Override
    public MoleculeManMiracleEffect copy() {
        return new MoleculeManMiracleEffect(this);
    }
}
