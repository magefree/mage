package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EldraziAngelToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class AngelicAberration extends CardImpl {

    public AngelicAberration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.ELDRAZI, SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Angelic Aberration enters the battlefield, sacrifice any number of creatures each with base power or toughness 1 or less.
        // Create that many 4/4 colorless Eldrazi Angel creature tokens with flying and vigilance.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AngelicAberrationEffect()));
    }

    private AngelicAberration(final AngelicAberration card) {
        super(card);
    }

    @Override
    public AngelicAberration copy() {
        return new AngelicAberration(this);
    }
}

class AngelicAberrationEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creatures with base power or toughness 1 or less");

    static {
        filter.add(Predicates.or(
                new PowerPredicate(ComparisonType.OR_LESS, 1),
                new ToughnessPredicate(ComparisonType.OR_LESS, 1)
        ));
    }

    AngelicAberrationEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice any number of creatures each with base power or toughness 1 or less. " +
                "Create that many 4/4 colorless Eldrazi Angel creature tokens with flying and vigilance";
    }

    private AngelicAberrationEffect(final AngelicAberrationEffect effect) {
        super(effect);
    }

    @Override
    public AngelicAberrationEffect copy() {
        return new AngelicAberrationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetSacrifice(0, Integer.MAX_VALUE, filter);
        if (!player.choose(outcome, target, source, game)) {
            return false;
        }
        int counter = 0;
        for (UUID permanentId : target.getTargets()) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null && permanent.sacrifice(source, game)) {
                counter++;
            }
        }
        return new CreateTokenEffect(new EldraziAngelToken(), counter).apply(game, source);
    }
}
