
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Plopman
 */
public final class GoblinWelder extends CardImpl {

    public GoblinWelder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Choose target artifact a player controls and target artifact card in that player's graveyard. If both targets are still legal as this ability resolves, that player simultaneously sacrifices the artifact and returns the artifact card to the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GoblinWelderEffect(), new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent(new FilterArtifactPermanent("artifact a player controls")));
        ability.addTarget(new GoblinWelderTarget());
        this.addAbility(ability);
    }

    public GoblinWelder(final GoblinWelder card) {
        super(card);
    }

    @Override
    public GoblinWelder copy() {
        return new GoblinWelder(this);
    }

    public static class GoblinWelderEffect extends OneShotEffect {

        public GoblinWelderEffect() {
            super(Outcome.PutCardInPlay);
            staticText = "Choose target artifact a player controls and target artifact card in that player's graveyard. If both targets are still legal as this ability resolves, that player simultaneously sacrifices the artifact and returns the artifact card to the battlefield";
        }

        public GoblinWelderEffect(final GoblinWelderEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent artifact = game.getPermanent(getTargetPointer().getFirst(game, source));
            Card card = game.getCard(source.getTargets().get(1).getFirstTarget());
            Player controller = game.getPlayer(source.getControllerId());
            if (artifact != null && card != null && controller != null) {
                Zone currentZone = game.getState().getZone(card.getId());
                Player owner = game.getPlayer(card.getOwnerId());
                if (owner != null
                        && artifact.isArtifact()
                        && card.isArtifact()
                        && currentZone == Zone.GRAVEYARD
                        && card.isOwnedBy(artifact.getControllerId())) {
                    boolean sacrifice = artifact.sacrifice(source.getSourceId(), game);
                    boolean putOnBF = owner.moveCards(card, Zone.BATTLEFIELD, source, game);
                    if (sacrifice || putOnBF) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public GoblinWelderEffect copy() {
            return new GoblinWelderEffect(this);
        }

    }

    static class GoblinWelderTarget extends TargetCardInGraveyard {

        public GoblinWelderTarget() {
            super(1, 1, new FilterArtifactCard());
            targetName = "target artifact card in that player's graveyard";
        }

        public GoblinWelderTarget(final GoblinWelderTarget target) {
            super(target);
        }

        @Override
        public boolean canTarget(UUID id, Ability source, Game game) {
            Permanent artifact = game.getPermanent(source.getFirstTarget());
            if (artifact == null) {
                return false;
            }
            Player player = game.getPlayer(artifact.getControllerId());
            Card card = game.getCard(id);
            if (card != null && player != null && player.getGraveyard().contains(id)) {
                return filter.match(card, game);
            }
            return false;
        }

        @Override
        public GoblinWelderTarget copy() {
            return new GoblinWelderTarget(this);
        }
    }

}
