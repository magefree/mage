package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;

/**
 * @author grimreap124
 */
public final class BleedingEffect extends CardImpl {

    public BleedingEffect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{B}");

        // At the beginning of combat on your turn, creatures you control gain flying until end of turn if a creature card in your graveyard has flying.
        // The same is true for first strike, double strike, deathtouch, hexproof, indestructible, lifelink, menace, reach, trample, and vigilance.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new BleedingEffectEffect(), TargetController.YOU, false));
    }

    private BleedingEffect(final BleedingEffect card) {
        super(card);
    }

    @Override
    public BleedingEffect copy() {
        return new BleedingEffect(this);
    }
}

class BleedingEffectEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filterFlying = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterFirstStrike = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterDoubleStrike = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterDeathtouch = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterHexproof = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterIndestructible = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterLifelink = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterMenace = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterReach = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterTrample = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterVigilance = new FilterControlledCreaturePermanent();
    
    private static final FilterControlledCreaturePermanent filterCreatures = new FilterControlledCreaturePermanent();

    static {
        filterFirstStrike.add(new AbilityPredicate(FirstStrikeAbility.class));
        filterFlying.add(new AbilityPredicate(FlyingAbility.class));
        filterDeathtouch.add(new AbilityPredicate(DeathtouchAbility.class));
        filterDoubleStrike.add(new AbilityPredicate(DoubleStrikeAbility.class));
        filterHexproof.add(new AbilityPredicate(HexproofBaseAbility.class));
        filterIndestructible.add(new AbilityPredicate(IndestructibleAbility.class));
        filterLifelink.add(new AbilityPredicate(LifelinkAbility.class));
        filterMenace.add(new AbilityPredicate(MenaceAbility.class));
        filterReach.add(new AbilityPredicate(ReachAbility.class));
        filterTrample.add(new AbilityPredicate(TrampleAbility.class));
        filterVigilance.add(new AbilityPredicate(VigilanceAbility.class));
    }

    BleedingEffectEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "creatures you control gain flying until end of turn if a creature card in your graveyard has flying." +
                " The same is true for first strike, double strike, deathtouch, hexproof, indestructible, lifelink, menace, reach, trample, and vigilance.";
    }

    private BleedingEffectEffect(final BleedingEffectEffect effect) {
        super(effect);
    }

    @Override
    public BleedingEffectEffect copy() {
        return new BleedingEffectEffect(this);
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