package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SavageAlliance extends CardImpl {

    private static final FilterPlayer filterPlayer = new FilterPlayer();
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent();
    private static final FilterPlayer filterOpponent = new FilterPlayer();

    static {
        filterOpponent.add(TargetController.OPPONENT.getPlayerPredicate());
    }

    public SavageAlliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Escalate {1}
        this.addAbility(new EscalateAbility(new GenericManaCost(1)));

        // Choose one or more &mdash;
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // Creatures target player controls gain trample until end of turn.
        this.getSpellAbility().addEffect(new SavageAllianceGainTrampleEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(1, 1, false, filterPlayer).withChooseHint("whose creatures gain trample"));

        // Savage Alliance deals 2 damage to target creature.;
        Effect effect = new DamageTargetEffect(2);
        effect.setText("{this} deals 2 damage to target creature");
        Mode mode = new Mode(effect);
        mode.addTarget(new TargetCreaturePermanent(filterCreature).withChooseHint("deals 2 damage to"));
        this.getSpellAbility().addMode(mode);

        // Savage Alliance deals 1 damage to each creature target opponent controls.
        mode = new Mode(new SavageAllianceDamageEffect());
        mode.addTarget(new TargetPlayer(1, 1, false, filterOpponent).withChooseHint("whose creatures get dealt 1 damage"));
        this.getSpellAbility().addMode(mode);
    }

    private SavageAlliance(final SavageAlliance card) {
        super(card);
    }

    @Override
    public SavageAlliance copy() {
        return new SavageAlliance(this);
    }
}

class SavageAllianceGainTrampleEffect extends OneShotEffect {

    public SavageAllianceGainTrampleEffect() {
        super(Outcome.AddAbility);
        staticText = "Creatures target player controls gain trample until end of turn";
    }

    public SavageAllianceGainTrampleEffect(final SavageAllianceGainTrampleEffect effect) {
        super(effect);
    }

    @Override
    public SavageAllianceGainTrampleEffect copy() {
        return new SavageAllianceGainTrampleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate((player.getId())));
            ContinuousEffect effect = new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filter);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class SavageAllianceDamageEffect extends OneShotEffect {

    public SavageAllianceDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 1 damage to each creature target opponent controls";
    }

    public SavageAllianceDamageEffect(final SavageAllianceDamageEffect effect) {
        super(effect);
    }

    @Override
    public SavageAllianceDamageEffect copy() {
        return new SavageAllianceDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(player.getId()));
            List<Permanent> creatures = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
            for (Permanent creature : creatures) {
                creature.damage(1, source.getSourceId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }
}
