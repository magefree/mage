package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.util.CardUtil;

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
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageMultiEffect());
        ability.addEffect(new RavenousGigantotheriumEffect());
        ability.addTarget(new TargetCreaturePermanentAmount(RavenousGigantotheriumAmount.instance)
                .withTargetName("up to X target creatures, where X is its power"));
        this.addAbility(ability);
    }

    private RavenousGigantotherium(final RavenousGigantotherium card) {
        super(card);
    }

    @Override
    public RavenousGigantotherium copy() {
        return new RavenousGigantotherium(this);
    }
}

enum RavenousGigantotheriumAmount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.getEffectValueFromAbility(sourceAbility, "permanentEnteredBattlefield", Permanent.class)
                .map(permanent -> permanent.getPower().getValue()).orElse(0);
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "its power";
    }

    @Override
    public String toString() {
        return "X";
    }
}

class RavenousGigantotheriumEffect extends OneShotEffect {

    RavenousGigantotheriumEffect() {
        super(Outcome.Benefit);
        this.setText("Each of those creatures deals damage equal to its power to {this}.");
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
