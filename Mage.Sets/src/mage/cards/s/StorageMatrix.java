package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StorageMatrix extends CardImpl {

    public StorageMatrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // As long as Storage Matrix is untapped, each player chooses artifact, creature, or land during their untap step. That player can untap only permanents of the chosen type this step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new StorageMatrixRestrictionEffect()));
    }

    private StorageMatrix(final StorageMatrix card) {
        super(card);
    }

    @Override
    public StorageMatrix copy() {
        return new StorageMatrix(this);
    }
}

class StorageMatrixRestrictionEffect extends RestrictionEffect {

    private int turn;
    private boolean applies;
    private static final Set<String> choice = new HashSet<>();

    static {
        choice.add(CardType.ARTIFACT.toString());
        choice.add(CardType.CREATURE.toString());
        choice.add(CardType.LAND.toString());
    }

    private CardType type;

    public StorageMatrixRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "As long as Storage Matrix is untapped, each player chooses artifact, creature, or land during their untap step. That player can untap only permanents of the chosen type this step";
    }

    public StorageMatrixRestrictionEffect(final StorageMatrixRestrictionEffect effect) {
        super(effect);
        this.type = effect.type;
        this.turn = effect.turn;
        this.applies = effect.applies;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (game.getTurnStepType() == PhaseStep.UNTAP) {
            if (game.getTurnNum() != turn) {
                turn = game.getTurnNum();
                applies = false;
                Permanent storageMatrix = game.getPermanent(source.getSourceId());
                if (storageMatrix != null && !storageMatrix.isTapped()) {
                    Choice choiceImpl = new ChoiceImpl(true);
                    choiceImpl.setMessage("Untap which kind of permanent?");
                    choiceImpl.setChoices(choice);
                    Player player = game.getPlayer(game.getActivePlayerId());
                    if (player != null && player.choose(outcome, choiceImpl, game)) {
                        String chosenType = choiceImpl.getChoice();
                        if (chosenType != null) {
                            game.informPlayers(storageMatrix.getLogName() + ": " + player.getLogName() + " chose to untap " + chosenType);

                            if (chosenType.equals(CardType.ARTIFACT.toString())) {
                                type = CardType.ARTIFACT;
                            } else if (chosenType.equals(CardType.LAND.toString())) {
                                type = CardType.LAND;
                            } else {
                                type = CardType.CREATURE;
                            }
                            applies = true;
                        }
                    }
                }
            }
            if (applies) {
                return !permanent.getCardType(game).contains(type);
            }
        }
        return false;
    }

    @Override
    public boolean canBeUntapped(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public StorageMatrixRestrictionEffect copy() {
        return new StorageMatrixRestrictionEffect(this);
    }
}
