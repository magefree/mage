package mage.cards.n;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes & L_J
 */
public final class Nebuchadnezzar extends CardImpl {

    public Nebuchadnezzar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {X}, {T}: Choose a card name. Target opponent reveals X cards at random from their hand. Then that player discards all cards with that name revealed this way. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL), new ManaCostsImpl<>("{X}"), MyTurnCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addEffect(new NebuchadnezzarEffect());
        ability.addTarget(new TargetOpponent());
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private Nebuchadnezzar(final Nebuchadnezzar card) {
        super(card);
    }

    @Override
    public Nebuchadnezzar copy() {
        return new Nebuchadnezzar(this);
    }
}

class NebuchadnezzarEffect extends OneShotEffect {

    public NebuchadnezzarEffect() {
        super(Outcome.Detriment);
        staticText = "Target opponent reveals X cards at random from their hand. Then that player discards all cards with that name revealed this way";
    }

    public NebuchadnezzarEffect(final NebuchadnezzarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source);
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (opponent == null || sourceObject == null || cardName.isEmpty()) {
            return false;
        }
        int costX = source.getManaCostsToPay().getX();
        if (costX <= 0 || opponent.getHand().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl();
        while (costX > 0) {
            Card card = opponent.getHand().getRandom(game);
            if (!cards.contains(card.getId())) {
                cards.add(card);
                costX--;
            }
            if (opponent.getHand().size() <= cards.size()) {
                break;
            }
        }
        opponent.revealCards(sourceObject.getIdName(), cards, game);
        cards.removeIf(uuid -> !cardName.equals(game.getCard(uuid).getName()));
        opponent.discard(cards, false, source, game);
        return true;
    }

    @Override
    public NebuchadnezzarEffect copy() {
        return new NebuchadnezzarEffect(this);
    }
}
