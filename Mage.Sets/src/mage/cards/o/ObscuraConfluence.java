package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObscuraConfluence extends CardImpl {

    public ObscuraConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{U}{B}");

        // Choose three. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setEachModeMoreThanOnce(true);

        // • Until end of turn, target creature loses all abilities and has base power and toughness 1/1.
        this.getSpellAbility().addEffect(new LoseAllAbilitiesTargetEffect(Duration.EndOfTurn)
                .setText("until end of turn, target creature loses all abilities"));
        this.getSpellAbility().addEffect(new SetBasePowerToughnessTargetEffect(1, 1, Duration.EndOfTurn)
                .setText("and has base power and toughness 1/1"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // • Target creature connives.
        this.getSpellAbility().addMode(new Mode(new ObscuraConfluenceConniveEffect()).addTarget(new TargetCreaturePermanent()));

        // • Target player returns a creature card from their graveyard to their hand.
        this.getSpellAbility().addMode(new Mode(new ObscuraConfluenceReturnEffect()).addTarget(new TargetPlayer()));
    }

    private ObscuraConfluence(final ObscuraConfluence card) {
        super(card);
    }

    @Override
    public ObscuraConfluence copy() {
        return new ObscuraConfluence(this);
    }
}

class ObscuraConfluenceConniveEffect extends OneShotEffect {

    ObscuraConfluenceConniveEffect() {
        super(Outcome.Benefit);
        staticText = "target creature connives. <i>(Draw a card, then discard a card. " +
                "If you discarded a nonland card, put a +1/+1 counter on that creature.)</i>";
    }

    private ObscuraConfluenceConniveEffect(final ObscuraConfluenceConniveEffect effect) {
        super(effect);
    }

    @Override
    public ObscuraConfluenceConniveEffect copy() {
        return new ObscuraConfluenceConniveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        return ConniveSourceEffect.connive(permanent, 1, source, game);
    }
}

class ObscuraConfluenceReturnEffect extends OneShotEffect {

    ObscuraConfluenceReturnEffect() {
        super(Outcome.Benefit);
        staticText = "target player returns a creature card from their graveyard to their hand";
    }

    private ObscuraConfluenceReturnEffect(final ObscuraConfluenceReturnEffect effect) {
        super(effect);
    }

    @Override
    public ObscuraConfluenceReturnEffect copy() {
        return new ObscuraConfluenceReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null || player.getGraveyard().count(
                StaticFilters.FILTER_CARD_CREATURE, game
        ) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        return player.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
    }
}
