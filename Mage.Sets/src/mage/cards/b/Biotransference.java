package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.command.Commander;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.NecronWarriorToken;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

public final class Biotransference extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an artifact spell");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public Biotransference(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // Creatures you control are artifacts in addition to their other types. The same is true for creature spells you control and creature cards you own that aren’t on the battlefield.
        this.addAbility(new SimpleStaticAbility(new BiotransferenceEffect()));

        // Whenever you cast an artifact spell, you lose 1 life and create a 2/2 black Necron Warrior artifact creature token.
        Ability ability = new SpellCastControllerTriggeredAbility(new LoseLifeSourceControllerEffect(1), filter, false);
        ability.addEffect(new CreateTokenEffect(new NecronWarriorToken(), 1).concatBy("and"));
        this.addAbility(ability);
    }

    private Biotransference(final Biotransference card) {
        super(card);
    }

    @Override
    public Biotransference copy() {
        return new Biotransference(this);
    }
}

class BiotransferenceEffect extends ContinuousEffectImpl {

    BiotransferenceEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Creatures you control are artifacts in addition to their other types. " +
                "The same is true for creature spells you control and creature cards you own that aren't on the battlefield";
        this.dependencyTypes.add(DependencyType.ArtifactAddingRemoving); // March of the Machines
    }

    private BiotransferenceEffect(final BiotransferenceEffect effect) {
        super(effect);
    }

    @Override
    public BiotransferenceEffect copy() {
        return new BiotransferenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // Creature cards you own that aren't on the battlefield
        // in graveyard
        for (UUID cardId : controller.getGraveyard()) {
            Card card = game.getCard(cardId);
            if (card != null && card.isCreature(game) && !card.isArtifact(game)) {
                card.addCardType(game, CardType.ARTIFACT);
            }
        }
        // on Hand
        for (UUID cardId : controller.getHand()) {
            Card card = game.getCard(cardId);
            if (card != null && card.isCreature(game) && !card.isArtifact(game)) {
                card.addCardType(game, CardType.ARTIFACT);
            }
        }
        // in Exile
        for (Card card : game.getState().getExile().getAllCards(game, source.getControllerId())) {
            if (card.isCreature(game) && !card.isArtifact(game)) {
                card.addCardType(game, CardType.ARTIFACT);
            }
        }
        // in Library (e.g. for Mystical Teachings)
        for (Card card : controller.getLibrary().getCards(game)) {
            if (card.isOwnedBy(controller.getId()) && card.isCreature(game) && !card.isArtifact(game)) {
                card.addCardType(game, CardType.ARTIFACT);
            }
        }
        // commander in command zone
        for (CommandObject commandObject : game.getState().getCommand()) {
            if (commandObject instanceof Commander) {
                Card card = game.getCard((commandObject).getId());
                if (card != null && card.isOwnedBy(controller.getId())
                        && card.isCreature(game) && !card.isArtifact(game)) {
                    card.addCardType(game, CardType.ARTIFACT);
                }
            }
        }

        // creature spells you control
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell
                    && stackObject.isControlledBy(source.getControllerId())
                    && stackObject.isCreature(game)
                    && !stackObject.isArtifact(game)) {
                Card card = ((Spell) stackObject).getCard();
                card.addCardType(game, CardType.ARTIFACT);
            }
        }
        // creatures you control
        List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(
                new FilterControlledCreaturePermanent(), source.getControllerId(), game);
        for (Permanent creature : creatures) {
            if (creature != null) {
                creature.addCardType(game, CardType.ARTIFACT);
            }
        }
        return true;

    }
}
