
package mage.cards.c;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PutTopCardOfYourLibraryToGraveyardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.ColorlessManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.MonoHybridManaCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class CharmedPendant extends CardImpl {

    public CharmedPendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {T}, Put the top card of your library into your graveyard: For each colored mana symbol in that card's mana cost, add one mana of that color. Activate this ability only any time you could cast an instant.
        this.addAbility(new CharmedPendantAbility());
    }

    public CharmedPendant(final CharmedPendant card) {
        super(card);
    }

    @Override
    public CharmedPendant copy() {
        return new CharmedPendant(this);
    }
}

class CharmedPendantAbility extends ActivatedManaAbilityImpl {

    public CharmedPendantAbility() {
        super(Zone.BATTLEFIELD, new CharmedPendantManaEffect(), new TapSourceCost());
        this.addCost(new PutTopCardOfYourLibraryToGraveyardCost());
        this.setUndoPossible(false); // Otherwise you could return the card from graveyard
    }

    public CharmedPendantAbility(Zone zone, Mana mana, Cost cost) {
        super(zone, new BasicManaEffect(mana), cost);

    }

    public CharmedPendantAbility(final CharmedPendantAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        if (player != null && !player.isInPayManaMode()) { // while paying the costs of a spell you cant activate this
            return super.canActivate(playerId, game);
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public CharmedPendantAbility copy() {
        return new CharmedPendantAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability only any time you could cast an instant.";
    }
}

class CharmedPendantManaEffect extends ManaEffect {

    public CharmedPendantManaEffect() {
        super();
        staticText = "For each colored mana symbol in that card's mana cost, add one mana of that color";
    }

    public CharmedPendantManaEffect(final CharmedPendantManaEffect effect) {
        super(effect);
    }

    @Override
    public CharmedPendantManaEffect copy() {
        return new CharmedPendantManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }

        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Mana mana = new Mana();
            for (Cost cost : source.getCosts()) {
                if (cost instanceof PutTopCardOfYourLibraryToGraveyardCost) {
                    Set<Card> cards = ((PutTopCardOfYourLibraryToGraveyardCost) cost).getCardsMovedToGraveyard();
                    if (!cards.isEmpty()) {
                        Card card = cards.iterator().next();
                        if (card != null && card.getManaCost() != null) {
                            ManaCosts<ManaCost> newManaCosts = new ManaCostsImpl<>();
                            for (ManaCost manaCost : card.getManaCost()) {
                                if (manaCost instanceof ColorlessManaCost || manaCost instanceof GenericManaCost || manaCost instanceof VariableManaCost) {
                                    continue;
                                }
                                if (manaCost instanceof MonoHybridManaCost) {
                                    newManaCosts.add(new ColoredManaCost(((MonoHybridManaCost) manaCost).getManaColor()));
                                } else {
                                    newManaCosts.add(manaCost.copy());
                                }
                            }
                            ManaOptions manaOptions = newManaCosts.getOptions();
                            if (manaOptions.size() == 1) {
                                mana = newManaCosts.getMana();
                            } else {
                                Choice manaChoice = new ChoiceImpl(true);
                                manaChoice.setMessage("Select which mana you like to produce");
                                for (Mana manaOption : manaOptions) {
                                    manaChoice.getChoices().add(manaOption.toString());
                                }
                                if (controller.choose(outcome, manaChoice, game)) {
                                    for (Mana manaOption : manaOptions) {
                                        if (manaChoice.getChoice().equals(manaOption.toString())) {
                                            mana = manaOption;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
            return mana;
        }

        return null;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.isTopCardRevealed()) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card != null) {
                    List<Mana> netMana = card.getManaCost().getManaOptions();
                    for (Mana mana : netMana) {
                        mana.setColorless(0);
                        mana.setGeneric(0);
                    }
                    return netMana;
                }
            }
        }
        return null;
    }
}
