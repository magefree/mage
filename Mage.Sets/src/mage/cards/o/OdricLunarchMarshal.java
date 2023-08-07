package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class OdricLunarchMarshal extends CardImpl {

    public OdricLunarchMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of each combat, creatures you control gain first strike until end of turn if a creature you control has first strike. The same is true for flying, deathtouch, double strike, haste, hexproof, indestructible, lifelink, menace, reach, skulk, trample, and vigilance.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new OdricLunarchMarshalEffect(), TargetController.ANY, false));
    }

    private OdricLunarchMarshal(final OdricLunarchMarshal card) {
        super(card);
    }

    @Override
    public OdricLunarchMarshal copy() {
        return new OdricLunarchMarshal(this);
    }
}

class OdricLunarchMarshalEffect extends OneShotEffect {

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
    private static final FilterControlledCreaturePermanent filterSkulk = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterTrample = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterVigilance = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterCreatures = new FilterControlledCreaturePermanent();

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
        filterSkulk.add(new AbilityPredicate(SkulkAbility.class));
        filterTrample.add(new AbilityPredicate(TrampleAbility.class));
        filterVigilance.add(new AbilityPredicate(VigilanceAbility.class));
    }

    OdricLunarchMarshalEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "creatures you control gain first strike until end of turn if a creature you control has first strike. The same is true for flying, deathtouch, double strike, haste, hexproof, indestructible, lifelink, menace, reach, skulk, trample, and vigilance.";
    }

    OdricLunarchMarshalEffect(final OdricLunarchMarshalEffect effect) {
        super(effect);
    }

    @Override
    public OdricLunarchMarshalEffect copy() {
        return new OdricLunarchMarshalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        // First strike
        if (game.getBattlefield().containsControlled(filterFirstStrike, source, game, 1)) {
            game.addEffect(new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Flying
        if (game.getBattlefield().containsControlled(filterFlying, source, game, 1)) {
            game.addEffect(new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Deathtouch
        if (game.getBattlefield().containsControlled(filterDeathtouch, source, game, 1)) {
            game.addEffect(new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Double strike
        if (game.getBattlefield().containsControlled(filterDoubleStrike, source, game, 1)) {
            game.addEffect(new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Haste
        if (game.getBattlefield().containsControlled(filterHaste, source, game, 1)) {
            game.addEffect(new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Hexproof
        if (game.getBattlefield().containsControlled(filterHexproof, source, game, 1)) {
            game.addEffect(new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Indestructible
        if (game.getBattlefield().containsControlled(filterIndestructible, source, game, 1)) {
            game.addEffect(new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Lifelink
        if (game.getBattlefield().containsControlled(filterLifelink, source, game, 1)) {
            game.addEffect(new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Menace
        if (game.getBattlefield().containsControlled(filterMenace, source, game, 1)) {
            game.addEffect(new GainAbilityControlledEffect(new MenaceAbility(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Reach
        if (game.getBattlefield().containsControlled(filterReach, source, game, 1)) {
            game.addEffect(new GainAbilityControlledEffect(ReachAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Skulk
        if (game.getBattlefield().containsControlled(filterSkulk, source, game, 1)) {
            game.addEffect(new GainAbilityControlledEffect(new SkulkAbility(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Trample
        if (game.getBattlefield().containsControlled(filterTrample, source, game, 1)) {
            game.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Vigilance
        if (game.getBattlefield().containsControlled(filterVigilance, source, game, 1)) {
            game.addEffect(new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }
        return true;
    }
}
