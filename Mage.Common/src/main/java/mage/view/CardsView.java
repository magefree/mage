package mage.view;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Dungeon;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.target.targetpointer.TargetPointer;
import mage.util.GameLog;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CardsView extends LinkedHashMap<UUID, CardView> {

    private static final Logger LOGGER = Logger.getLogger(CardsView.class);

    public CardsView() {
    }

    /**
     * Non game usage like card render tests
     *
     * @param cardViews
     */
    public CardsView(List<CardView> cardViews) {
        for (CardView view : cardViews) {
            this.put(view.getId(), view);
        }
    }

    /**
     * Non game usage like deck editor
     *
     * @param cards
     */
    public CardsView(Collection<? extends Card> cards) {
        for (Card card : cards) {
            this.put(card.getId(), new CardView(card));
        }
    }

    public CardsView(Game game, Collection<? extends Card> cards) {
        for (Card card : cards) {
            this.put(card.getId(), new CardView(card, game, false));
        }
    }

    public CardsView(Game game, Collection<? extends Card> cards, boolean showFaceDown, boolean storeZone) {
        for (Card card : cards) {
            this.put(card.getId(), new CardView(card, game, false, showFaceDown, storeZone));
        }
    }

    public CardsView(Collection<? extends Ability> abilities, Game game) {
        for (Ability ability : abilities) {
            MageObject sourceObject = null;
            AbilityView abilityView = null;
            boolean isCard = false;
            boolean isPermanent = false;
            switch (ability.getZone()) {
                case ALL:
                case EXILED:
                case GRAVEYARD:
                    sourceObject = game.getCard(ability.getSourceId());
                    if (sourceObject == null) {
                        sourceObject = game.getPermanent(ability.getSourceId());
                    }
                    if (sourceObject == null) {
                        sourceObject = game.getObject(ability.getSourceId());
                        if (sourceObject instanceof PermanentToken) {
                            isPermanent = true;
                        }
                    } else {
                        isCard = true;
                    }
                    break;
                case BATTLEFIELD:
                    sourceObject = game.getPermanent(ability.getSourceId());
                    if (sourceObject == null) {
                        sourceObject = game.getLastKnownInformation(ability.getSourceId(), Zone.BATTLEFIELD);
                    }
                    isPermanent = true;
                    break;
                case STACK:
                case HAND: // Miracle
                case LIBRARY:
                case OUTSIDE:
                    sourceObject = game.getObject(ability.getSourceId());
                    if (sourceObject instanceof Card) {
                        isCard = true;
                    }
                    break;
                case COMMAND:
                    sourceObject = game.getObject(ability.getSourceId());
                    if (sourceObject instanceof Emblem) {
//                        Card sourceCard = (Card) ((Emblem) sourceObject).getSourceObject();
//                        if (sourceCard == null) {
//                            throw new IllegalArgumentException("Source card for emblem not found.");
//                        }
                        abilityView = new AbilityView(ability, sourceObject.getName(), new CardView(new EmblemView((Emblem) sourceObject)));
                        abilityView.setName(sourceObject.getName());
                        // abilityView.setExpansionSetCode(sourceCard.getExpansionSetCode());
                    } else if (sourceObject instanceof Dungeon) {
                        abilityView = new AbilityView(ability, sourceObject.getName(), new CardView(new DungeonView((Dungeon) sourceObject)));
                        abilityView.setName(sourceObject.getName());
                    } else if (sourceObject instanceof Plane) {
                        abilityView = new AbilityView(ability, sourceObject.getName(), new CardView(new PlaneView((Plane) sourceObject)));
                        abilityView.setName(sourceObject.getName());
                    }
                    break;
            }
            if (sourceObject != null) {
                if (abilityView == null) {
                    CardView sourceCardView;
                    if (isPermanent) {
                        sourceCardView = new CardView((Permanent) sourceObject, game);
                    } else if (isCard) {
                        sourceCardView = new CardView((Card) sourceObject, game);
                    } else {
                        sourceCardView = new CardView(sourceObject, game);
                    }
                    abilityView = new AbilityView(ability, sourceObject.getName(), sourceCardView);
                }
                if (!ability.getTargets().isEmpty()) {
                    abilityView.addTargets(ability.getTargets(), ability.getEffects(), ability, game);
                } else {
                    List<UUID> abilityTargets = new ArrayList<>();
                    for (Effect effect : ability.getEffects()) {
                        TargetPointer targetPointer = effect.getTargetPointer();
                        if (targetPointer != null) {
                            List<UUID> targetList = targetPointer.getTargets(game, ability);
                            abilityTargets.addAll(targetList);
                        }
                    }
                    if (!abilityTargets.isEmpty()) {
                        abilityView.overrideTargets(abilityTargets);
                        List<String> names = new ArrayList<>();
                        for (UUID uuid : abilityTargets) {
                            MageObject mageObject = game.getObject(uuid);
                            if (mageObject != null) {
                                if ((mageObject instanceof Card) && ((Card) mageObject).isFaceDown(game)) {
                                    continue;
                                }
                                String newName = GameLog.getColoredObjectIdNameForTooltip(mageObject);
                                if (!names.contains(newName)) {
                                    names.add(newName);
                                }
                            }
                        }
                        if (!names.isEmpty()) {
                            abilityView.getRules().add("<i>Related objects: " + names.toString() + "</i>");
                        }
                    }
                }
                this.put(ability.getId(), abilityView);
            }
        }

        if (this.size() != abilities.size()) {
            LOGGER.error("Can't translate abilities list to cards view (need " + abilities.size() + ", but get " + this.size() + "). Abilities:\n"
                    + abilities.stream().map(a -> a.getClass().getSimpleName() + " - " + a.getRule()).collect(Collectors.joining("\n")));
        }
    }
}
