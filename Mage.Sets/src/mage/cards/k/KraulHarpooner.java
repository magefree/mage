package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.*;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class KraulHarpooner extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with flying you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public KraulHarpooner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Undergrowth — When Kraul Harpooner enters the battlefield, choose up to one target creature with flying you don't control. Kraul Harpooner gets +X/+0 until end of turn, where X is the number of creature cards in your graveyard, then you may have Kraul Harpooner fight that creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new KraulHarpoonerEffect(), false
        );
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        ability.setAbilityWord(AbilityWord.UNDERGROWTH);
        this.addAbility(ability);
    }

    private KraulHarpooner(final KraulHarpooner card) {
        super(card);
    }

    @Override
    public KraulHarpooner copy() {
        return new KraulHarpooner(this);
    }
}

class KraulHarpoonerEffect extends OneShotEffect {

    public KraulHarpoonerEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose up to one target creature with flying "
                + "you don't control. {this} gets +X/+0 until end of turn, "
                + "where X is the number of creature cards in your graveyard, "
                + "then you may have {this} fight that creature.";
    }

    public KraulHarpoonerEffect(final KraulHarpoonerEffect effect) {
        super(effect);
    }

    @Override
    public KraulHarpoonerEffect copy() {
        return new KraulHarpoonerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePerm = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (sourcePerm == null || player == null) {
            return false;
        }
        int xValue = player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
        game.addEffect(new BoostSourceEffect(xValue, 0, Duration.EndOfTurn), source);
        game.getState().processAction(game);
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null || !player.chooseUse(outcome, "Have " + sourcePerm.getLogName() + " fight " + creature.getLogName() + "?", source, game)) {
            return true;
        }
        return creature.fight(sourcePerm, source, game);
    }
}
