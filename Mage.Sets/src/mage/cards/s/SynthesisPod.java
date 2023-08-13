package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SynthesisPod extends CardImpl {

    public SynthesisPod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{U/P}");

        // ({U/P} can be paid with either {U} or 2 life.)
        this.addAbility(new SimpleStaticAbility(new InfoEffect("<i>({U/P} can be paid with either {U} or 2 life.)</i>")));

        // {1}{U/P}, {T}, Exile a spell you control: Target opponent reveals cards from the top of their library until they reveal a card with mana value equal to 1 plus the exiled spell's mana value. Exile that card, then that player shuffles. You may cast that exiled card without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(new SynthesisPodEffect(), new ManaCostsImpl<>("{1}{U/P}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SynthesisPodCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SynthesisPod(final SynthesisPod card) {
        super(card);
    }

    @Override
    public SynthesisPod copy() {
        return new SynthesisPod(this);
    }
}

class SynthesisPodCost extends CostImpl {

    private static final FilterSpell filter = new FilterSpell("a spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    private Spell exiledSpell = null;

    SynthesisPodCost() {
        super();
        TargetSpell target = new TargetSpell(filter);
        target.setNotTarget(true);
        this.addTarget(target);
        this.text = "Exile a spell you control";
    }

    private SynthesisPodCost(SynthesisPodCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return false;
        }
        player.chooseTarget(Outcome.Exile, targets.get(0), source, game);
        Spell spell = game.getSpell(targets.getFirstTarget());
        if (spell == null) {
            return false;
        }
        String spellName = spell.getName();
        exiledSpell = spell;
        player.moveCards(spell.getCard(), Zone.EXILED, source, game);
        paid = true;
        game.informPlayers(player.getLogName() + " exiles " + spellName + " (as costs)");
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, source, game);
    }

    @Override
    public SynthesisPodCost copy() {
        return new SynthesisPodCost(this);
    }

    Spell getExiledSpell() {
        return exiledSpell;
    }
}

class SynthesisPodEffect extends OneShotEffect {

    SynthesisPodEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent reveals cards from the top of their library until they reveal a card " +
                "with mana value equal to 1 plus the exiled spell's mana value. Exile that card, " +
                "then that player shuffles. You may cast that exiled card without paying its mana cost";
    }

    private SynthesisPodEffect(final SynthesisPodEffect effect) {
        super(effect);
    }

    @Override
    public SynthesisPodEffect copy() {
        return new SynthesisPodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Spell spell = CardUtil
                .castStream(source.getCosts(), SynthesisPodCost.class)
                .map(SynthesisPodCost::getExiledSpell)
                .findFirst()
                .orElse(null);
        if (controller == null || opponent == null || spell == null) {
            return false;
        }
        int mv = spell.getManaValue();
        Cards cards = new CardsImpl();
        Card card = getCard(mv, opponent, cards, game);
        opponent.revealCards(source, cards, game);
        if (card != null) {
            controller.moveCards(card, Zone.EXILED, source, game);
        }
        opponent.shuffleLibrary(source, game);
        if (card != null) {
            CardUtil.castSpellWithAttributesForFree(controller, source, game, card);
        }
        return true;
    }

    private static Card getCard(int mv, Player opponent, Cards cards, Game game) {
        for (Card card : opponent.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.getManaValue() == mv + 1) {
                return card;
            }
        }
        return null;
    }
}
