package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class MalevolentWitchkite extends CardImpl {

    public MalevolentWitchkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Malevolent Witchkite enters the battlefield, sacrifice any number of artifacts, enchantments, and/or tokens, then draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MalevolentWitchkiteEffect()));
    }

    private MalevolentWitchkite(final MalevolentWitchkite card) {
        super(card);
    }

    @Override
    public MalevolentWitchkite copy() {
        return new MalevolentWitchkite(this);
    }
}

class MalevolentWitchkiteEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifacts, enchantments, and/or tokens");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                TokenPredicate.TRUE
        ));
    }

    MalevolentWitchkiteEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice any number of artifacts, enchantments, and/or tokens, "
                + "then draw that many cards";
    }

    private MalevolentWitchkiteEffect(final MalevolentWitchkiteEffect effect) {
        super(effect);
    }

    @Override
    public MalevolentWitchkiteEffect copy() {
        return new MalevolentWitchkiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Target target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }

        controller.chooseTarget(Outcome.Sacrifice, target, source, game);
        List<Permanent> toSacrifice = target
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        int sacrificeCount = 0;
        if (!toSacrifice.isEmpty()) {
            sacrificeCount = toSacrifice.size();
            game.informPlayers(controller.getLogName() + " chose " + sacrificeCount
                    + " permanents to sacrifice. " + CardUtil.getSourceLogName(game, source));

            new SacrificeTargetEffect()
                    .setTargetPointer(new FixedTargets(toSacrifice, game))
                    .apply(game, source);
        }

        new DrawCardSourceControllerEffect(sacrificeCount).apply(game, source);
        return true;
    }

}