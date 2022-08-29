package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MajesticMyriarch extends CardImpl {

    public MajesticMyriarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.CHIMERA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Majestic Myriarch's power and toughness are each equal to twice the number of creatures you control.
        DynamicValue xValue = new MultipliedValue(new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent()), 2);
        Effect effect = new SetBasePowerToughnessSourceEffect(xValue, Duration.EndOfGame);
        effect.setText("{this}'s power and toughness are each equal to twice the number of creatures you control");
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));

        // At the beginning of each combat, if you control a creature with flying, Majestic Myriarch gains flying until end of turn.
        // The same is true for first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, menace, reach, trample, and vigilance.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new MajesticMyriarchEffect(), TargetController.ANY, false));
    }

    private MajesticMyriarch(final MajesticMyriarch card) {
        super(card);
    }

    @Override
    public MajesticMyriarch copy() {
        return new MajesticMyriarch(this);
    }
}

class MajesticMyriarchEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filterFirstStrike = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterFlying = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterDeathtouch = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterDoubleStrike = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterHaste = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterHexproof = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterIndestructible = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterLifelink = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterMenace = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterReach = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterTrample = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterVigilance = new FilterControlledCreaturePermanent();

    static {
        filterFirstStrike.add(new AbilityPredicate(FirstStrikeAbility.class));
        filterFlying.add(new AbilityPredicate(FlyingAbility.class));
        filterDeathtouch.add(new AbilityPredicate(DeathtouchAbility.class));
        filterDoubleStrike.add(new AbilityPredicate(DoubleStrikeAbility.class));
        filterHaste.add(new AbilityPredicate(HasteAbility.class));
        filterHexproof.add(new AbilityPredicate(HexproofBaseAbility.class));
        filterIndestructible.add(new AbilityPredicate(IndestructibleAbility.class));
        filterLifelink.add(new AbilityPredicate(LifelinkAbility.class));
        filterMenace.add(new AbilityPredicate(MenaceAbility.class));
        filterReach.add(new AbilityPredicate(ReachAbility.class));
        filterTrample.add(new AbilityPredicate(TrampleAbility.class));
        filterVigilance.add(new AbilityPredicate(VigilanceAbility.class));
    }

    MajesticMyriarchEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "if you control a creature with flying, Majestic Myriarch gains flying until end of turn. " +
                "The same is true for first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, menace, reach, trample, and vigilance.";
    }

    MajesticMyriarchEffect(final MajesticMyriarchEffect effect) {
        super(effect);
    }

    @Override
    public MajesticMyriarchEffect copy() {
        return new MajesticMyriarchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        // Flying
        if (game.getBattlefield().containsControlled(filterFlying, source, game, 1)) {
            game.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), source);
        }

        // First strike
        if (game.getBattlefield().containsControlled(filterFirstStrike, source, game, 1)) {
            game.addEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), source);
        }

        // Double strike
        if (game.getBattlefield().containsControlled(filterDoubleStrike, source, game, 1)) {
            game.addEffect(new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn), source);
        }

        // Deathtouch
        if (game.getBattlefield().containsControlled(filterDeathtouch, source, game, 1)) {
            game.addEffect(new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn), source);
        }

        // Haste
        if (game.getBattlefield().containsControlled(filterHaste, source, game, 1)) {
            game.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn), source);
        }

        // Hexproof
        if (game.getBattlefield().containsControlled(filterHexproof, source, game, 1)) {
            game.addEffect(new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.EndOfTurn), source);
        }

        // Indestructible
        if (game.getBattlefield().containsControlled(filterIndestructible, source, game, 1)) {
            game.addEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), source);
        }

        // Lifelink
        if (game.getBattlefield().containsControlled(filterLifelink, source, game, 1)) {
            game.addEffect(new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn), source);
        }

        // Menace
        if (game.getBattlefield().containsControlled(filterMenace, source, game, 1)) {
            game.addEffect(new GainAbilitySourceEffect(new MenaceAbility(), Duration.EndOfTurn), source);
        }

        // Reach
        if (game.getBattlefield().containsControlled(filterReach, source, game, 1)) {
            game.addEffect(new GainAbilitySourceEffect(ReachAbility.getInstance(), Duration.EndOfTurn), source);
        }

        // Trample
        if (game.getBattlefield().containsControlled(filterTrample, source, game, 1)) {
            game.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), source);
        }

        // Vigilance
        if (game.getBattlefield().containsControlled(filterVigilance, source, game, 1)) {
            game.addEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn), source);
        }
        return true;
    }
}