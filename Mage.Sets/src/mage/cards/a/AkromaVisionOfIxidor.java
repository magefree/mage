package mage.cards.a;

import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AkromaVisionOfIxidor extends CardImpl {

    public AkromaVisionOfIxidor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of each combat, until end of turn, each other creature you control gets +1/+1 if it has flying, +1/+1 if it has first strike, and so on for double strike, deathtouch, haste, hexproof, indestructible, lifelink, menace, protection, reach, trample, vigilance, and partner.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new AkromaVisionOfIxidorEffect(), TargetController.ANY, false
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private AkromaVisionOfIxidor(final AkromaVisionOfIxidor card) {
        super(card);
    }

    @Override
    public AkromaVisionOfIxidor copy() {
        return new AkromaVisionOfIxidor(this);
    }
}

class AkromaVisionOfIxidorEffect extends OneShotEffect {

    private static final Set<Class<? extends Ability>> classes = new HashSet<>(Arrays.asList(
            FlyingAbility.class,
            FirstStrikeAbility.class,
            DoubleStrikeAbility.class,
            DeathtouchAbility.class,
            HasteAbility.class,
            HexproofBaseAbility.class,
            IndestructibleAbility.class,
            LifelinkAbility.class,
            MenaceAbility.class,
            ProtectionAbility.class,
            ReachAbility.class,
            TrampleAbility.class,
            VigilanceAbility.class,
            PartnerAbility.class
    ));

    AkromaVisionOfIxidorEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, each other creature you control gets +1/+1 if it has flying, " +
                "+1/+1 if it has first strike, and so on for double strike, deathtouch, haste, hexproof, " +
                "indestructible, lifelink, menace, protection, reach, trample, vigilance, and partner";
    }

    private AkromaVisionOfIxidorEffect(final AkromaVisionOfIxidorEffect effect) {
        super(effect);
    }

    @Override
    public AkromaVisionOfIxidorEffect copy() {
        return new AkromaVisionOfIxidorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE,
                source.getControllerId(), source.getSourceId(), game
        )) {
            Abilities abilities = permanent.getAbilities(game);
            int count = classes
                    .stream()
                    .map(clazz -> abilities.stream().anyMatch(clazz::isInstance))
                    .mapToInt(b -> b ? 1 : 0)
                    .sum();
            if (count > 0) {
                ContinuousEffect effect = new BoostTargetEffect(count, count, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(permanent.getId(), game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
