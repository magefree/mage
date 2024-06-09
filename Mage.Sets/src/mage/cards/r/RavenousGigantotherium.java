package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class RavenousGigantotherium extends CardImpl {

    public RavenousGigantotherium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devour 3
        this.addAbility(new DevourAbility(3));

        // When Ravenous Gigantotherium enters the battlefield, it deals X damage divided as you choose among up to X target creatures, where X is its power. Each of those creatures deals damage equal to its power to Ravenous Gigantotherium.
        this.addAbility(new RavenousGigantotheriumAbility());
    }

    private RavenousGigantotherium(final RavenousGigantotherium card) {
        super(card);
    }

    @Override
    public RavenousGigantotherium copy() {
        return new RavenousGigantotherium(this);
    }
}

class RavenousGigantotheriumAbility extends EntersBattlefieldTriggeredAbility {

    RavenousGigantotheriumAbility() {
        super(null, false);
    }

    private RavenousGigantotheriumAbility(final RavenousGigantotheriumAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null) {
            return false;
        }
        int power = Math.max(permanent.getPower().getValue(), 0);
        this.getEffects().clear();
        this.addEffect(new DamageMultiEffect(power));
        this.addEffect(new RavenousGigantotheriumEffect());
        this.getTargets().clear();
        if (power < 1) {
            return true;
        }
        this.addTarget(new TargetCreaturePermanentAmount(power));
        return true;
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield, it deals X damage " +
                "divided as you choose among up to X target creatures, where X is its power. " +
                "Each of those creatures deals damage equal to its power to {this}.";
    }

    @Override
    public RavenousGigantotheriumAbility copy() {
        return new RavenousGigantotheriumAbility(this);
    }
}

class RavenousGigantotheriumEffect extends OneShotEffect {

    RavenousGigantotheriumEffect() {
        super(Outcome.Benefit);
    }

    private RavenousGigantotheriumEffect(final RavenousGigantotheriumEffect effect) {
        super(effect);
    }

    @Override
    public RavenousGigantotheriumEffect copy() {
        return new RavenousGigantotheriumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePerm = game.getPermanent(source.getSourceId());
        if (sourcePerm == null) {
            return false;
        }
        List<Permanent> permanentList = source
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        for (Permanent permanent : permanentList) {
            sourcePerm.damage(permanent.getPower().getValue(), permanent.getId(), source, game);
        }
        return true;
    }
}