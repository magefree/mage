package mage.view;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameState;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.target.targetpointer.TargetPointer;
import mage.util.GameLog;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CardsView extends LinkedHashMap<UUID, CardView> {

    public CardsView() {
    }

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
                        sourceCardView = new CardView((Permanent) sourceObject);
                    } else if (isCard) {
                        sourceCardView = new CardView((Card) sourceObject);
                    } else {
                        sourceCardView = new CardView(sourceObject);
                    }
                    abilityView = new AbilityView(ability, sourceObject.getName(), sourceCardView);
                }
                if (!ability.getTargets().isEmpty()) {
                    abilityView.setTargets(ability.getTargets());
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
    }

    public CardsView(Collection<? extends Ability> abilities, GameState state) {
        for (Ability ability : abilities) {
            Card sourceCard = state.getPermanent(ability.getSourceId());
            if (sourceCard != null) {
                this.put(ability.getId(), new AbilityView(ability, sourceCard.getName(), new CardView(sourceCard)));
            }
        }
    }

}
