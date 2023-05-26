
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class DarthTyranusCountOfSerenno extends CardImpl {

    public DarthTyranusCountOfSerenno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{1}{W}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOOKU);

        this.setStartingLoyalty(3);

        // +1: Up to one target creature gets -6/-0 until your next turn.
        Effect effect = new BoostTargetEffect(-6, 0, Duration.UntilYourNextTurn);
        effect.setText("Up to one target creature gets -6/-0 until your next turn");
        Ability ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -3: Sacrifice an artifact. If you do, you may search your library for an artifact card and put that card onto the battlefield. Shuffle your library.
        this.addAbility(new LoyaltyAbility(new TransmuteArtifactEffect(), -3));

        // -6: Target player's life total becomes 5. Another target players's life total becomes 30.
        ability = new LoyaltyAbility(new DarthTyranusEffect(), -6);
        ability.addTarget(new TargetPlayer(2));
        this.addAbility(ability);
    }

    private DarthTyranusCountOfSerenno(final DarthTyranusCountOfSerenno card) {
        super(card);
    }

    @Override
    public DarthTyranusCountOfSerenno copy() {
        return new DarthTyranusCountOfSerenno(this);
    }
}

class DarthTyranusEffect extends OneShotEffect {

    public DarthTyranusEffect() {
        super(Outcome.Benefit);
        staticText = "Target player's life total becomes 5. Another target players's life total becomes 30";
    }

    public DarthTyranusEffect(DarthTyranusEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player1 = game.getPlayer(targetPointer.getTargets(game, source).get(0));
        Player player2 = game.getPlayer(targetPointer.getTargets(game, source).get(1));
        if (player1 != null && player2 != null) {
            player1.setLife(5, game, source);
            player1.setLife(30, game, source);
            return true;
        }
        return false;
    }

    @Override
    public DarthTyranusEffect copy() {
        return new DarthTyranusEffect(this);
    }
}

class TransmuteArtifactEffect extends SearchEffect {

    public TransmuteArtifactEffect() {
        super(new TargetCardInLibrary(new FilterArtifactCard()), Outcome.PutCardInPlay);
        staticText = "Sacrifice an artifact. If you do, search your library for an artifact card and put that card onto the battlefield. Shuffle your library";
    }

    public TransmuteArtifactEffect(final TransmuteArtifactEffect effect) {
        super(effect);
    }

    @Override
    public TransmuteArtifactEffect copy() {
        return new TransmuteArtifactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean sacrifice = false;
            TargetControlledPermanent targetArtifact = new TargetControlledPermanent(new FilterControlledArtifactPermanent());
            if (controller.chooseTarget(Outcome.Sacrifice, targetArtifact, source, game)) {
                Permanent permanent = game.getPermanent(targetArtifact.getFirstTarget());
                if (permanent != null) {
                    sacrifice = permanent.sacrifice(source, game);
                }
            }
            if (sacrifice && controller.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    for (UUID cardId : target.getTargets()) {
                        Card card = controller.getLibrary().getCard(cardId, game);
                        if (card != null) {
                            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                            controller.shuffleLibrary(source, game);
                            return true;
                        }
                    }
                }
                controller.shuffleLibrary(source, game);
            }
        }
        return false;
    }
}
