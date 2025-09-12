package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.MultiAmountType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public final class MasterOfTheWildHunt extends CardImpl {

    public MasterOfTheWildHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, create a 2/2 green Wolf creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new WolfToken())));

        // {T}: Tap all untapped Wolf creatures you control. Each Wolf tapped this way deals damage equal to its power to target creature. That creature deals damage equal to its power divided as its controller chooses among any number of those Wolves.
        Ability ability = new SimpleActivatedAbility(new MasterOfTheWildHuntEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addHint(new ValueHint("Untapped wolfs you control", new PermanentsOnBattlefieldCount(MasterOfTheWildHuntEffect.filter)));
        this.addAbility(ability);
    }

    private MasterOfTheWildHunt(final MasterOfTheWildHunt card) {
        super(card);
    }

    @Override
    public MasterOfTheWildHunt copy() {
        return new MasterOfTheWildHunt(this);
    }
}

class MasterOfTheWildHuntEffect extends OneShotEffect {

    protected static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(SubType.WOLF.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public MasterOfTheWildHuntEffect() {
        super(Outcome.Damage);
        staticText = "Tap all untapped Wolf creatures you control. Each Wolf tapped this way deals damage equal to its power to target creature. That creature deals damage equal to its power divided as its controller chooses among any number of those Wolves";
    }

    private MasterOfTheWildHuntEffect(final MasterOfTheWildHuntEffect effect) {
        super(effect);
    }

    @Override
    public MasterOfTheWildHuntEffect copy() {
        return new MasterOfTheWildHuntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> wolves = new ArrayList<>();
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }
        Player player = game.getPlayer(target.getControllerId());
        if (player == null) {
            return false;
        }

        // Tap all untapped Wolf creatures you control
        // Each Wolf tapped this way deals damage equal to its power to target creature
        if (game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                permanent.tap(source, game);
                target.damage(permanent.getPower().getValue(), permanent.getId(), source, game);
                wolves.add(permanent);
            }
        }

        if (wolves.isEmpty()) {
            return true;
        }

        // That creature deals damage equal to its power divided as its controller chooses among any number of those Wolves
        int totalDamage = target.getPower().getValue();
        List<String> messages = new ArrayList<>();
        wolves.forEach(permanent -> {
            String info = String.format("%s, P/T: %d/%d",
                    permanent.getLogName(),
                    permanent.getPower().getValue(),
                    permanent.getToughness().getValue()
            );
            messages.add(info);
        });
        List<Integer> damagesList = player.getMultiAmount(Outcome.Damage, messages, 0, totalDamage, totalDamage, MultiAmountType.DAMAGE, game);
        if (damagesList.size() == wolves.size()) {
            for (int i = 0; i < wolves.size(); i++) {
                wolves.get(i).damage(damagesList.get(i), target.getId(), source, game, false, true);
            }
        } else {
            throw new IllegalArgumentException("Wrong code usage: getMultiAmount must return same size");
        }

        return true;
    }

}
