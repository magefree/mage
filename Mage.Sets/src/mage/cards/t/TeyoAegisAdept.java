package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetPerpetuallyEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetPerpetuallyEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.emblems.TeyoAegisAdeptEmblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author karapuzz14
 */
public final class TeyoAegisAdept extends CardImpl {

    public TeyoAegisAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEYO);
        this.setStartingLoyalty(4);

        // +1: Up to one target creature’s base power perpetually becomes equal to its toughness. It perpetually gains “This creature can attack as though it didn’t have defender.”
        Ability firstLoyaltyAbility = new LoyaltyAbility(new TeyoAegisAdeptFirstEffect(), 1);
        Ability canAttackAbility = new SimpleStaticAbility(Zone.ALL, new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield));
        firstLoyaltyAbility.addEffect(new GainAbilityTargetPerpetuallyEffect(canAttackAbility)
                .setText("It perpetually gains \"This creature can attack as though it didn't have defender.\""));
        firstLoyaltyAbility.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(firstLoyaltyAbility);

        // −2: Conjure a card named Lumbering Lightshield onto the battlefield.
        this.addAbility(new LoyaltyAbility(new ConjureCardEffect("Lumbering Lightshield", Zone.BATTLEFIELD, 1), -2));

        // −6: You get an emblem with “At the beginning of your end step, return target white creature card from your graveyard to the battlefield. You gain life equal to its toughness.”
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TeyoAegisAdeptEmblem()), -6));
    }

    private TeyoAegisAdept(final TeyoAegisAdept card) {
        super(card);
    }

    @Override
    public TeyoAegisAdept copy() {
        return new TeyoAegisAdept(this);
    }
}

class TeyoAegisAdeptFirstEffect extends OneShotEffect {

    TeyoAegisAdeptFirstEffect() {
        super(Outcome.BoostCreature);
        staticText = "Up to one target creature's base power perpetually becomes equal to its toughness";
    }

    private TeyoAegisAdeptFirstEffect(final TeyoAegisAdeptFirstEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            game.addEffect(new SetBasePowerToughnessTargetPerpetuallyEffect(StaticValue.get(permanent.getToughness().getValue()), null), source);
        }
        return false;
    }

    @Override
    public TeyoAegisAdeptFirstEffect copy() {
        return new TeyoAegisAdeptFirstEffect(this);
    }
}