
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class DepalaPilotExemplar extends CardImpl {

    public DepalaPilotExemplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Dwarves you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, new FilterCreaturePermanent(SubType.DWARF, "Dwarves"), true)));

        // Each Vehicle you control gets +1/+1 as long as it's a creature.
        Effect effect = new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, new FilterCreaturePermanent(SubType.VEHICLE, "Vehicle"));
        effect.setText("Each Vehicle you control gets +1/+1 as long as it's a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Whenever Depala, Pilot Exemplar becomes tapped, you may pay {X}. If you do, reveal the top X cards of your library, put all Dwarf and Vehicle cards from among them into your hand, then put the rest on the bottom of your library in a random order.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new DepalaPilotExemplarEffect(), true));
    }

    private DepalaPilotExemplar(final DepalaPilotExemplar card) {
        super(card);
    }

    @Override
    public DepalaPilotExemplar copy() {
        return new DepalaPilotExemplar(this);
    }
}

class DepalaPilotExemplarEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Dwarf and Vehicle cards");

    static {
        filter.add(Predicates.or(SubType.DWARF.getPredicate(), SubType.VEHICLE.getPredicate()));
    }

    DepalaPilotExemplarEffect() {
        super(Outcome.DrawCard);
        this.staticText = "pay {X}. If you do, reveal the top X cards of your library, put all Dwarf and Vehicle cards from among them into your hand, then put the rest on the bottom of your library in a random order";
    }

    DepalaPilotExemplarEffect(final DepalaPilotExemplarEffect effect) {
        super(effect);
    }

    @Override
    public DepalaPilotExemplarEffect copy() {
        return new DepalaPilotExemplarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ManaCosts<ManaCost> cost = new ManaCostsImpl<>("{X}");
            int xValue = controller.announceXMana(0, Integer.MAX_VALUE, "Choose the amount of mana to pay", game, source);
            cost.add(new GenericManaCost(xValue));
            if (cost.pay(source, game, source, source.getControllerId(), false) && xValue > 0) {
                new RevealLibraryPutIntoHandEffect(xValue, filter, Zone.LIBRARY, false).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
