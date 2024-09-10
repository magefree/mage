package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HustleBustle extends SplitCard {
    public HustleBustle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{U/R}", "{4}{R/G}{R/G}", SpellAbilityType.SPLIT);

        // Target creature attacks or blocks this turn if able.
        this.getLeftHalfCard().getSpellAbility().addEffect(new AttacksIfAbleTargetEffect(Duration.EndOfTurn)
                .setText("target creature attacks"));
        this.getLeftHalfCard().getSpellAbility().addEffect(new BlocksIfAbleTargetEffect(Duration.EndOfTurn)
                .setText("or blocks this turn if able"));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Bustle
        // {4}{R/G}{R/G}
        // Sorcery
        // Creatures you control get +2/+2 and gain trample until end of turn. You may turn a face-down creature you control face up.
        this.getRightHalfCard().getSpellAbility().addEffect(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn
        ).setText("creatures you control get +2/+2"));
        this.getRightHalfCard().getSpellAbility().addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain trample until end of turn"));
        this.getRightHalfCard().getSpellAbility().addEffect(new HustleBustleEffect());
    }

    private HustleBustle(final HustleBustle card) {
        super(card);
    }

    @Override
    public HustleBustle copy() {
        return new HustleBustle(this);
    }
}

class HustleBustleEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("face-down creature you control");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    HustleBustleEffect() {
        super(Outcome.Benefit);
        staticText = "You may turn a face-down creature you control face up";
    }

    private HustleBustleEffect(final HustleBustleEffect effect) {
        super(effect);
    }

    @Override
    public HustleBustleEffect copy() {
        return new HustleBustleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, 1, filter, true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.turnFaceUp(source, game, source.getControllerId());
    }
}
