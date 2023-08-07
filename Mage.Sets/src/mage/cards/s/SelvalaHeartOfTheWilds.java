package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author maxlebedev
 */
public final class SelvalaHeartOfTheWilds extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final String rule = "Whenever another creature enters the battlefield, its controller may draw a card if its power is greater than each other creature's power.";

    public SelvalaHeartOfTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature enters the battlefield, its controller may draw a card if its power is greater than each other creature's power.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new SelvalaHeartOfTheWildsEffect(), filter, false, SetTargetPointer.PERMANENT, rule));

        // {G}, {T}: Add X mana in any combination of colors, where X is the greatest power among creatures you control.
        ManaEffect manaEffect = new AddManaInAnyCombinationEffect(
                GreatestPowerAmongControlledCreaturesValue.instance, GreatestPowerAmongControlledCreaturesValue.instance,
                ColoredManaSymbol.W, ColoredManaSymbol.U, ColoredManaSymbol.B, ColoredManaSymbol.R, ColoredManaSymbol.G);
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, manaEffect, new ManaCostsImpl<>("{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private SelvalaHeartOfTheWilds(final SelvalaHeartOfTheWilds card) {
        super(card);
    }

    @Override
    public SelvalaHeartOfTheWilds copy() {
        return new SelvalaHeartOfTheWilds(this);
    }
}

class SelvalaHeartOfTheWildsEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent();

    static {
        filter2.add(new GreatestPowerPredicate());
    }

    public SelvalaHeartOfTheWildsEffect() {
        super(Outcome.Benefit);
        this.staticText = "that creature's controller may draw a card";
    }

    public SelvalaHeartOfTheWildsEffect(final SelvalaHeartOfTheWildsEffect effect) {
        super(effect);
    }

    @Override
    public SelvalaHeartOfTheWildsEffect copy() {
        return new SelvalaHeartOfTheWildsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            if (filter2.match(permanent, game)) {
                Player permanentController = game.getPlayer(permanent.getControllerId());
                if (permanentController != null
                        && permanentController.chooseUse(Outcome.DrawCard, "Draw a card?", source, game)) {
                    permanentController.drawCards(1, source, game);
                }
            }
            return true;
        }
        return false;
    }
}

class GreatestPowerPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        int power = input.getPower().getValue();
        for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game)) {
                    if (permanent.getPower().getValue() >= power && !permanent.equals(input)) {
                        return false; //we found something with equal/more power
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Greatest Power";
    }
}
