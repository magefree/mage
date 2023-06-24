package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.CompoundAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class EsikaGodOfTheTree extends ModalDoubleFacedCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creatures");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public EsikaGodOfTheTree(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{1}{G}{G}",
                "The Prismatic Bridge",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{W}{U}{B}{R}{G}"
        );

        // 1.
        // Esika, God of the Tree
        // Legendary Creature - God
        this.getLeftHalfCard().setPT(new MageInt(1), new MageInt(4));

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // {T}: Add one mana of any color.
        this.getLeftHalfCard().addAbility(new AnyColorManaAbility());

        // Other legendary creatures you control have vigilance and "{T}: Add one mana of any color."
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new CompoundAbility(VigilanceAbility.getInstance(), new AnyColorManaAbility()),
                Duration.WhileOnBattlefield, filter, true
        ).setText("other legendary creatures you control have vigilance and \"{T}: Add one mana of any color.\"")));

        // 2.
        // The Prismatic Bridge
        // Legendary Enchantment
        // At the beginning of your upkeep, reveal cards from the top of your library until you reveal a creature or planeswalker card. Put that card onto the battlefield and the rest on the bottom of your library in a random order.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new PrismaticBridgeEffect(), TargetController.YOU, false, false
        ));
    }

    private EsikaGodOfTheTree(final EsikaGodOfTheTree card) {
        super(card);
    }

    @Override
    public EsikaGodOfTheTree copy() {
        return new EsikaGodOfTheTree(this);
    }
}

class PrismaticBridgeEffect extends OneShotEffect {

    public PrismaticBridgeEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "reveal cards from the top of your library until you reveal "
                + "a creature or planeswalker card. Put that card onto the battlefield and the rest "
                + "on the bottom of your library in a random order";
    }

    private PrismaticBridgeEffect(final PrismaticBridgeEffect effect) {
        super(effect);
    }

    @Override
    public PrismaticBridgeEffect copy() {
        return new PrismaticBridgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards toReveal = new CardsImpl();
        Card toBattlefield = null;
        for (Card card : controller.getLibrary().getCards(game)) {
            toReveal.add(card);
            if (card.isCreature(game) || card.isPlaneswalker(game)) {
                toBattlefield = card;
                break;
            }
        }
        controller.revealCards(source, toReveal, game);
        if (toBattlefield != null) {
            toReveal.remove(toBattlefield);
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
        }
        controller.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        return true;
    }
}
