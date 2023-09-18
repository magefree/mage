package mage.cards.c;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.MillCardsCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.*;
import mage.abilities.effects.mana.ManaEffect;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CharmedPendant extends CardImpl {

    public CharmedPendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {T}, Put the top card of your library into your graveyard: For each colored mana symbol in that card's mana cost, add one mana of that color. Activate this ability only any time you could cast an instant.
        this.addAbility(new CharmedPendantAbility());
    }

    private CharmedPendant(final CharmedPendant card) {
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
        this.addCost(new MillCardsCost());
        this.setUndoPossible(false); // Otherwise you could return the card from graveyard
    }

    public CharmedPendantAbility(Zone zone, Mana mana, Cost cost) {
        super(zone, new BasicManaEffect(mana), cost);

    }

    private CharmedPendantAbility(final CharmedPendantAbility ability) {
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
        return super.getRule() + " Activate only as an instant.";
    }
}

class CharmedPendantManaEffect extends ManaEffect {

    public CharmedPendantManaEffect() {
        super();
        staticText = "For each colored mana symbol in the milled card's mana cost, add one mana of that color";
    }

    private CharmedPendantManaEffect(final CharmedPendantManaEffect effect) {
        super(effect);
    }

    @Override
    public CharmedPendantManaEffect copy() {
        return new CharmedPendantManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game == null) {
            return new ArrayList<>();
        }
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
        return new ArrayList<>();
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Cost cost : source.getCosts()) {
                if (cost instanceof MillCardsCost) {
                    Set<Card> cards = ((MillCardsCost) cost).getCardsMovedToGraveyard();
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
                                if (manaChoice.getChoices().isEmpty()) {  // no mana choices available
                                    return mana;
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
        }
        return mana;
    }
}
