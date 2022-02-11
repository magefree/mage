package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;
import mage.filter.common.FilterArtifactCard;

/**
 * @author Plopman
 */
public final class GoblinWelder extends CardImpl {

    public GoblinWelder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Choose target artifact a player controls and target artifact card in that player's graveyard. If both targets are still legal as this ability resolves, that player simultaneously sacrifices the artifact and returns the artifact card to the battlefield.
        Ability ability = new SimpleActivatedAbility(new GoblinWelderEffect(), new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        ability.addTarget(new GoblinWelderTarget());
        this.addAbility(ability);
    }

    private GoblinWelder(final GoblinWelder card) {
        super(card);
    }

    @Override
    public GoblinWelder copy() {
        return new GoblinWelder(this);
    }

    private static class GoblinWelderEffect extends OneShotEffect {

        public GoblinWelderEffect() {
            super(Outcome.PutCardInPlay);
            staticText = "Choose target artifact a player controls "
                    + "and target artifact card in that player's graveyard. "
                    + "If both targets are still legal as this ability resolves, "
                    + "that player simultaneously sacrifices the artifact "
                    + "and returns the artifact card to the battlefield";
            this.setTargetPointer(new EachTargetPointer());
        }

        public GoblinWelderEffect(final GoblinWelderEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            if (getTargetPointer().getTargets(game, source).size() < 2) {
                return false;
            }
            Permanent artifact = game.getPermanent(getTargetPointer().getFirst(game, source));
            Card card = game.getCard(getTargetPointer().getTargets(game, source).get(1));
            Player controller = game.getPlayer(source.getControllerId());
            if (artifact == null
                    || card == null
                    || controller == null) {
                return false;
            }
            Player owner = game.getPlayer(card.getOwnerId());
            if (owner == null) {
                return false;
            }
            boolean sacrifice = artifact.sacrifice(source, game);
            game.getState().processAction(game); // bug #7672
            boolean putOnBF = owner.moveCards(card, Zone.BATTLEFIELD, source, game);
            if (sacrifice || putOnBF) {
                return true;
            }
            return false;
        }

        @Override
        public GoblinWelderEffect copy() {
            return new GoblinWelderEffect(this);
        }

    }

    private static class GoblinWelderTarget extends TargetCardInGraveyard {

        public GoblinWelderTarget() {
            super(new FilterArtifactCard());
            targetName = "target artifact card in that player's graveyard";
        }

        public GoblinWelderTarget(final GoblinWelderTarget target) {
            super(target);
        }

        @Override
        public boolean canTarget(UUID id, Ability source, Game game) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent == null) {
                return false;
            }
            Card card = game.getCard(id);
            return card != null
                    && card.isArtifact(game)
                    && card.isOwnedBy(permanent.getControllerId());
        }

        @Override
        public GoblinWelderTarget copy() {
            return new GoblinWelderTarget(this);
        }
    }
}
