package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author padfoothelix
 */
public final class ThisIsHowItEnds extends CardImpl {

    public ThisIsHowItEnds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Target creature's owner shuffles it into their library, then faces a villainous choice -- They lose 5 life, or they shuffle another creature they own into their library.
        this.getSpellAbility().addEffect(
                new ShuffleIntoLibraryTargetEffect()
                        .setText("target creature's owner shuffles it into their library,")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ThisIsHowItEndsEffect());
    }

    private ThisIsHowItEnds(final ThisIsHowItEnds card) {
        super(card);
    }

    @Override
    public ThisIsHowItEnds copy() {
        return new ThisIsHowItEnds(this);
    }
}

class ThisIsHowItEndsEffect extends OneShotEffect {

    private static final FaceVillainousChoice choice = new FaceVillainousChoice(
            Outcome.Removal, new ThisIsHowItEndsFirstChoice(), new ThisIsHowItEndsSecondChoice()
    );

    ThisIsHowItEndsEffect() {
        super(Outcome.Benefit);
        staticText = "then " + choice.generateRule();
    }

    private ThisIsHowItEndsEffect(final ThisIsHowItEndsEffect effect) {
        super(effect);
    }

    @Override
    public ThisIsHowItEndsEffect copy() {
        return new ThisIsHowItEndsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetOwnerId = game.getOwnerId(getTargetPointer().getFirst(game, source));
        Player targetOwner = game.getPlayer(targetOwnerId);
        choice.faceChoice(targetOwner, game, source);
        return true;
    }
}

class ThisIsHowItEndsFirstChoice extends VillainousChoice {
    ThisIsHowItEndsFirstChoice() {
        super("They lose 5 life", "You lose 5 life");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        player.loseLife(5, game, source, false);
        return true;
    }
}

class ThisIsHowItEndsSecondChoice extends VillainousChoice {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    ThisIsHowItEndsSecondChoice() {
        super("they shuffle another creature they own into their library", "you shuffle a creature you own into your library");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        target.withChooseHint("to shuffle into your library");
        player.chooseTarget(Outcome.Detriment, target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        player.shuffleCardsToLibrary(cards, game, source);
        return true;
    }
}
