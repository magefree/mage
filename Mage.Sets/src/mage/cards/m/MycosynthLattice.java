package mage.cards.m;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.hint.StaticHint;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.ManaPoolItem;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author duncant
 */
public final class MycosynthLattice extends CardImpl {

    public MycosynthLattice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // All permanents are artifacts in addition to their other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PermanentsAreArtifactsEffect()));

        // All cards that aren't on the battlefield, spells, and permanents are colorless.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EverythingIsColorlessEffect()));

        // Players may spend mana as though it were mana of any color.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ManaCanBeSpentAsAnyColorEffect());
        ability.addHint(new StaticHint("(XMage hint: You can use floating mana by clicking on the related symbol of the needed mana type in your mana pool player area.)"));
        this.addAbility(ability);
    }

    private MycosynthLattice(final MycosynthLattice card) {
        super(card);
    }

    @Override
    public MycosynthLattice copy() {
        return new MycosynthLattice(this);
    }
}

class PermanentsAreArtifactsEffect extends ContinuousEffectImpl {

    public PermanentsAreArtifactsEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "All permanents are artifacts in addition to their other types";
        this.dependencyTypes.add(DependencyType.ArtifactAddingRemoving); // March of the Machines
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            perm.addCardType(game, CardType.ARTIFACT);
        }
        return true;
    }

    @Override
    public PermanentsAreArtifactsEffect copy() {
        return new PermanentsAreArtifactsEffect(this);
    }

    private PermanentsAreArtifactsEffect(PermanentsAreArtifactsEffect effect) {
        super(effect);
    }
}

class EverythingIsColorlessEffect extends ContinuousEffectImpl {

    public EverythingIsColorlessEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Neutral);
        staticText = "All cards that aren't on the battlefield, spells, and permanents are colorless";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ObjectColor colorless = new ObjectColor();

            // permaments
            for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                perm.getColor(game).setColor(colorless);
            }

            List<Card> affectedCards = new ArrayList<>();

            // spells
            for (MageObject object : game.getStack()) {
                if (object instanceof Spell) {
                    game.getState().getCreateMageObjectAttribute(object, game).getColor().setColor(colorless);

                    Card card = ((Spell) object).getCard();
                    affectedCards.add(card);
                }
            }

            // exile
            affectedCards.addAll(game.getExile().getAllCardsByRange(game, controller.getId()));


            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player == null) {
                    continue;
                }

                // command
                affectedCards.addAll(game.getCommanderCardsFromCommandZone(player, CommanderCardType.ANY));

                // hand
                affectedCards.addAll(player.getHand().getCards(game));

                // library
                affectedCards.addAll(player.getLibrary().getCards(game));

                // graveyard
                affectedCards.addAll(player.getGraveyard().getCards(game));
            }


            // apply colors to all cards
            affectedCards.forEach(card -> {
                game.getState().getCreateMageObjectAttribute(card, game).getColor().setColor(colorless);

                // mdf cards
                if (card instanceof ModalDoubleFacedCard) {
                    ModalDoubleFacedCardHalf leftHalfCard = ((ModalDoubleFacedCard) card).getLeftHalfCard();
                    ModalDoubleFacedCardHalf rightHalfCard = ((ModalDoubleFacedCard) card).getRightHalfCard();
                    game.getState().getCreateMageObjectAttribute(leftHalfCard, game).getColor().setColor(colorless);
                    game.getState().getCreateMageObjectAttribute(rightHalfCard, game).getColor().setColor(colorless);
                }

                // split cards
                if (card instanceof SplitCard) {
                    SplitCardHalf leftHalfCard = ((SplitCard) card).getLeftHalfCard();
                    SplitCardHalf rightHalfCard = ((SplitCard) card).getRightHalfCard();
                    game.getState().getCreateMageObjectAttribute(leftHalfCard, game).getColor().setColor(colorless);
                    game.getState().getCreateMageObjectAttribute(rightHalfCard, game).getColor().setColor(colorless);
                }

                // double faces cards
                if (card.getSecondCardFace() != null) {
                    game.getState().getCreateMageObjectAttribute(card, game).getColor().setColor(colorless);
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public EverythingIsColorlessEffect copy() {
        return new EverythingIsColorlessEffect(this);
    }

    private EverythingIsColorlessEffect(EverythingIsColorlessEffect effect) {
        super(effect);
    }
}

class ManaCanBeSpentAsAnyColorEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public ManaCanBeSpentAsAnyColorEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Players may spend mana as though it were mana of any color";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return game.getState().getPlayersInRange(source.getControllerId(), game).contains(affectedControllerId);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }

    @Override
    public ManaCanBeSpentAsAnyColorEffect copy() {
        return new ManaCanBeSpentAsAnyColorEffect(this);
    }

    private ManaCanBeSpentAsAnyColorEffect(ManaCanBeSpentAsAnyColorEffect effect) {
        super(effect);
    }
}
