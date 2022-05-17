package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KamizObscuraOculus extends CardImpl {

    public KamizObscuraOculus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CEPHALID);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you attack, target attacking creature can't be blocked this turn. It connives. Then choose another attacking creature with lesser power. That creature gains double strike until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new CantBeBlockedTargetEffect(), 1);
        ability.addEffect(new KamizObscuraOculusEffect());
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private KamizObscuraOculus(final KamizObscuraOculus card) {
        super(card);
    }

    @Override
    public KamizObscuraOculus copy() {
        return new KamizObscuraOculus(this);
    }
}

class KamizObscuraOculusEffect extends OneShotEffect {

    KamizObscuraOculusEffect() {
        super(Outcome.Benefit);
        staticText = "It connives. Then choose another attacking creature with lesser power. " +
                "That creature gains double strike until end of turn";
    }

    private KamizObscuraOculusEffect(final KamizObscuraOculusEffect effect) {
        super(effect);
    }

    @Override
    public KamizObscuraOculusEffect copy() {
        return new KamizObscuraOculusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        ConniveSourceEffect.connive(permanent, 1, source, game);
        FilterPermanent filter = new FilterAttackingCreature("another attacking creature with lesser power");
        filter.add(Predicates.not(new PermanentIdPredicate(permanent.getId())));
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, permanent.getPower().getValue()));
        if (!game.getBattlefield().contains(filter, source, game, 1)) {
            return true;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        game.addEffect(new GainAbilityTargetEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTarget(target.getFirstTarget(), game)), source);
        return true;
    }
}
