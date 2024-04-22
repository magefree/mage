package mage.cards.e;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EowynFearlessKnight extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("creature an opponent controls with greater power");

    static {
        filter.add(EowynFearlessKnightPredicate.instance);
    }

    public EowynFearlessKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Eowyn, Fearless Knight enters the battlefield, exile target creature an opponent controls with greater power. Legendary creatures you control gain protection from each of that creature's colors until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EowynFearlessKnightEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private EowynFearlessKnight(final EowynFearlessKnight card) {
        super(card);
    }

    @Override
    public EowynFearlessKnight copy() {
        return new EowynFearlessKnight(this);
    }
}

enum EowynFearlessKnightPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(permanent -> permanent.getPower().getValue()
                        < input.getObject().getPower().getValue()).orElse(false);
    }
}

class EowynFearlessKnightEffect extends OneShotEffect {

    EowynFearlessKnightEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature an opponent controls with greater power. Legendary creatures " +
                "you control gain protection from each of that creature's colors until end of turn";
    }

    private EowynFearlessKnightEffect(final EowynFearlessKnightEffect effect) {
        super(effect);
    }

    @Override
    public EowynFearlessKnightEffect copy() {
        return new EowynFearlessKnightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        player.moveCards(permanent, Zone.EXILED, source, game);
        for (ObjectColor color : permanent.getColor(game).getColors()) {
            game.addEffect(new GainAbilityControlledEffect(
                    ProtectionAbility.from(color), Duration.EndOfTurn,
                    StaticFilters.FILTER_CREATURE_LEGENDARY
            ), source);
        }
        return true;
    }
}
