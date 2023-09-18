package mage.cards.d;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DomriAnarchOfBolas extends CardImpl {

    public DomriAnarchOfBolas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOMRI);
        this.setStartingLoyalty(3);

        // Creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield)
        ));

        // +1: Add {R} or {G}. Creature spells you cast this turn can't be countered.
        this.addAbility(new LoyaltyAbility(new DomriAnarchOfBolasEffect(), 1));

        // -2: Target creature you control fights target creature you don't control.
        Ability ability = new LoyaltyAbility(new FightTargetsEffect(false), -2);
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private DomriAnarchOfBolas(final DomriAnarchOfBolas card) {
        super(card);
    }

    @Override
    public DomriAnarchOfBolas copy() {
        return new DomriAnarchOfBolas(this);
    }
}

class DomriAnarchOfBolasEffect extends OneShotEffect {

    DomriAnarchOfBolasEffect() {
        super(Outcome.Benefit);
        staticText = "Add {R} or {G}. Creature spells you cast this turn can't be countered.";
    }

    private DomriAnarchOfBolasEffect(final DomriAnarchOfBolasEffect effect) {
        super(effect);
    }

    @Override
    public DomriAnarchOfBolasEffect copy() {
        return new DomriAnarchOfBolasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Mana mana = new Mana();
        if (player.chooseUse(outcome, "Choose a color of mana to add", null, "Red", "Green", source, game)) {
            mana.increaseRed();
        } else {
            mana.increaseGreen();
        }
        player.getManaPool().addMana(mana, game, source);
        game.addEffect(new CantBeCounteredControlledEffect(StaticFilters.FILTER_SPELL_A_CREATURE, Duration.EndOfTurn), source);
        return true;
    }
}
