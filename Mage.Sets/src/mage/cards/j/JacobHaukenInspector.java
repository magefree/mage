package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class JacobHaukenInspector extends CardImpl {

    public JacobHaukenInspector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.h.HaukensInsight.class;

        // {T}: Draw a card, then exile a card from your hand face down. You may look at that card for as long as it remains exiled. You may pay {4}{U}{U}. If you do, transform Jacob Hauken, Inspector.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(new JacobHaukenInspectorExileEffect(), new TapSourceCost());
        ability.addEffect(new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{4}{U}{U}")));
        this.addAbility(ability);
    }

    private JacobHaukenInspector(final JacobHaukenInspector card) {
        super(card);
    }

    @Override
    public JacobHaukenInspector copy() {
        return new JacobHaukenInspector(this);
    }
}

class JacobHaukenInspectorExileEffect extends OneShotEffect {

    JacobHaukenInspectorExileEffect() {
        super(Outcome.Benefit);
        staticText = "Draw a card, then exile a card from your hand face down. You may look at that card for as long as it remains exiled";
    }

    private JacobHaukenInspectorExileEffect(final JacobHaukenInspectorExileEffect effect) {
        super(effect);
    }

    @Override
    public JacobHaukenInspectorExileEffect copy() {
        return new JacobHaukenInspectorExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.drawCards(1, source, game);
        if (!controller.getHand().isEmpty()) {
            TargetCardInHand target = new TargetCardInHand().withChooseHint("to exile");
            controller.chooseTarget(outcome, controller.getHand(), target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                CardUtil.moveCardToExileFaceDown(game, source , controller, card,true);
            }
        }
        return true;
    }
}