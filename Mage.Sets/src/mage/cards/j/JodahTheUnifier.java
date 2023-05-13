package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class JodahTheUnifier extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("legendary creatures you control");

    private static final FilterSpell filter2
            = new FilterSpell("a legendary spell from your hand");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(new CastFromZonePredicate(Zone.HAND));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public JodahTheUnifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Legendary creatures you control get +X/+X where X is the number of legendary creatures you control.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                xValue, xValue, Duration.WhileOnBattlefield, filter, false
        )));

        // Whenever you cast a legendary spell from your hand, exile cards from the top of your library until you exile a legendary nonland card with lesser mana value. You may cast that card without paying its mana cost. Put the rest on the bottom of your library in a random order.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new JodahTheUnifierEffect(), filter2, false
        ));
    }

    private JodahTheUnifier(final JodahTheUnifier card) {
        super(card);
    }

    @Override
    public JodahTheUnifier copy() {
        return new JodahTheUnifier(this);
    }
}

class JodahTheUnifierEffect extends OneShotEffect {

    public JodahTheUnifierEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "exile cards from the top of your library until you exile a legendary nonland card with lesser mana value. You may cast that card without paying its mana cost. Put the rest on the bottom of your library in a random order.";
    }

    private JodahTheUnifierEffect(final JodahTheUnifierEffect effect) {
        super(effect);
    }

    @Override
    public JodahTheUnifierEffect copy() {
        return new JodahTheUnifierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Object spellCast = this.getValue("spellCast");
        if (!(spellCast instanceof Spell)) {
            return false;
        }
        int manaValue = ((Spell) spellCast).getManaValue();
        Cards exiledCards = new CardsImpl();
        for (Card card : controller.getLibrary().getCards(game)) {
            exiledCards.add(card);
            controller.moveCards(card, Zone.EXILED, source, game);
            game.getState().processAction(game);
            if (card.isLegendary(game) && !card.isLand(game) && card.getManaValue() < manaValue) {
                CardUtil.castSpellWithAttributesForFree(controller, source, game, card);
                break;
            }
        }
        exiledCards.retainZone(Zone.EXILED, game);
        controller.putCardsOnBottomOfLibrary(exiledCards, game, source, false);
        return true;
    }
}
