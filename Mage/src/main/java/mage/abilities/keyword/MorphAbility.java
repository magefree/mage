package mage.abilities.keyword;

import java.util.Iterator;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AlternativeCost2Impl;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect.FaceDownType;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * 702.36. Morph
 *
 * 702.36a Morph is a static ability that functions in any zone from which you
 * could play the card its on, and the morph effect works any time the card is
 * face down. "Morph [cost]" means "You may cast this card as a 2/2 face-down
 * creature, with no text, no name, no subtypes, and no mana cost by paying {3}
 * rather than paying its mana cost." (See rule 707, "Face-Down Spells and
 * Permanents.")
 *
 * 702.36b To cast a card using its morph ability, turn it face down. It becomes
 * a 2/2 face-down creature card, with no text, no name, no subtypes, and no
 * mana cost. Any effects or prohibitions that would apply to casting a card
 * with these characteristics (and not the face-up cards characteristics) are
 * applied to casting this card. These values are the copiable values of that
 * object's characteristics. (See rule 613, "Interaction of Continuous Effects,"
 * and rule 706, "Copying Objects.") Put it onto the stack (as a face-down spell
 * with the same characteristics), and pay {3} rather than pay its mana cost.
 * This follows the rules for paying alternative costs. You can use morph to
 * cast a card from any zone from which you could normally play it. When the
 * spell resolves, it enters the battlefield with the same characteristics the
 * spell had. The morph effect applies to the face-down object wherever it is,
 * and it ends when the permanent is turned face up. #
 *
 * 702.36c You can't cast a card face down if it doesn't have morph.
 *
 * 702.36d If you have priority, you may turn a face-down permanent you control
 * face up. This is a special action; it doesn't use the stack (see rule 115).
 * To do this, show all players what the permanents morph cost would be if it
 * were face up, pay that cost, then turn the permanent face up. (If the
 * permanent wouldn't have a morph cost if it were face up, it cant be turned
 * face up this way.) The morph effect on it ends, and it regains its normal
 * characteristics. Any abilities relating to the permanent entering the
 * battlefield dont trigger when its turned face up and dont have any effect,
 * because the permanent has already entered the battlefield.
 *
 * 702.36e See rule 707, "Face-Down Spells and Permanents," for more information
 * on how to cast cards with morph.
 *
 * @author LevelX2
 */
public class MorphAbility extends StaticAbility implements AlternativeSourceCosts {

    protected static final String ABILITY_KEYWORD = "Morph";
    protected static final String ABILITY_KEYWORD_MEGA = "Megamorph";
    protected static final String REMINDER_TEXT = "<i>(You may cast this card face down as a "
            + "2/2 creature for {3}. Turn it face up any time for its morph cost.)</i>";
    protected static final String REMINDER_TEXT_MEGA = "<i>(You may cast this card face down "
            + "as a 2/2 creature for {3}. Turn it face up any time for its megamorph "
            + "cost and put a +1/+1 counter on it.)</i>";
    protected String ruleText;
    protected AlternativeCost2Impl alternateCosts = new AlternativeCost2Impl(
            ABILITY_KEYWORD, REMINDER_TEXT, new GenericManaCost(3));
    protected Costs<Cost> morphCosts;
    // needed to check activation status, if card changes zone after casting it
    private int zoneChangeCounter = 0;
    private boolean megamorph;

    public MorphAbility(Cost morphCost) {
        this(createCosts(morphCost));
    }

    public MorphAbility(Cost morphCost, boolean megamorph) {
        this(createCosts(morphCost), megamorph);
    }

    public MorphAbility(Costs<Cost> morphCosts) {
        this(morphCosts, false);
    }

    public MorphAbility(Costs<Cost> morphCosts, boolean megamorph) {
        super(Zone.HAND, null);
        this.morphCosts = morphCosts;
        this.megamorph = megamorph;
        this.setWorksFaceDown(true);
        StringBuilder sb = new StringBuilder();
        if (megamorph) {
            sb.append(ABILITY_KEYWORD_MEGA).append(' ');
        } else {
            sb.append(ABILITY_KEYWORD).append(' ');
        }
        name = ABILITY_KEYWORD;
        for (Cost cost : morphCosts) {
            if (!(cost instanceof ManaCosts)) {
                sb.setLength(sb.length() - 1);
                sb.append("&mdash;");
                break;
            }
        }
        sb.append(morphCosts.getText());
        if (!(morphCosts.get(morphCosts.size() - 1) instanceof ManaCosts)) {
            sb.append('.');
        }
        sb.append(' ');
        if (megamorph) {
            sb.append(REMINDER_TEXT_MEGA);
        } else {
            sb.append(REMINDER_TEXT);
        }

        ruleText = sb.toString();

        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesFaceDownCreatureEffect(
                morphCosts, (megamorph ? FaceDownType.MEGAMORPHED : FaceDownType.MORPHED)));
        ability.setWorksFaceDown(true);
        ability.setRuleVisible(false);
        addSubAbility(ability);

    }

    public MorphAbility(final MorphAbility ability) {
        super(ability);
        this.zoneChangeCounter = ability.zoneChangeCounter;
        this.ruleText = ability.ruleText;
        this.alternateCosts = ability.alternateCosts.copy();
        this.morphCosts = ability.morphCosts; // can't be changed
        this.megamorph = ability.megamorph;
    }

    private static Costs<Cost> createCosts(Cost cost) {
        Costs<Cost> costs = new CostsImpl<>();
        costs.add(cost);
        return costs;
    }

    @Override
    public MorphAbility copy() {
        return new MorphAbility(this);
    }

    public void resetMorph() {
        alternateCosts.reset();
        zoneChangeCounter = 0;
    }

    public Costs<Cost> getMorphCosts() {
        return morphCosts;
    }

    @Override
    public boolean isActivated(Ability ability, Game game) {
        Card card = game.getCard(sourceId);
        if (card != null
                && card.getZoneChangeCounter(game) <= zoneChangeCounter + 1) {
            return alternateCosts.isActivated(game);
        }
        return false;
    }

    @Override
    public boolean isAvailable(Ability source, Game game) {
        return true;
    }

    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        if (ability.getAbilityType() == AbilityType.SPELL) {
            Player player = game.getPlayer(ability.getControllerId());
            Spell spell = game.getStack().getSpell(ability.getId());
            if (player != null
                    && spell != null) {
                this.resetMorph();
                spell.setFaceDown(true, game); // so only the back is visible
                if (alternateCosts.canPay(ability, this, ability.getControllerId(), game)) {
                    if (player.chooseUse(Outcome.Benefit, "Cast this card as a 2/2 "
                            + "face-down creature for " + getCosts().getText() + " ?", ability, game)) {
                        game.getState().setValue("MorphAbility"
                                + ability.getSourceId(), "activated"); // Gift of Doom
                        activateMorph(game);
                        // change mana costs
                        ability.getManaCostsToPay().clear();
                        ability.getCosts().clear();
                        for (Iterator it = this.alternateCosts.iterator(); it.hasNext();) {
                            Cost cost = (Cost) it.next();
                            if (cost instanceof ManaCost) {
                                ability.getManaCostsToPay().add((ManaCost) cost.copy());
                            } else {
                                ability.getCosts().add(cost.copy());
                            }
                        }
                        // change spell colors and subtype *TODO probably this needs to be done by continuous effect (while on the stack)
                        ObjectColor spellColor = spell.getColor(game);
                        spellColor.setBlack(false);
                        spellColor.setRed(false);
                        spellColor.setGreen(false);
                        spellColor.setWhite(false);
                        spellColor.setBlue(false);
                        game.getState().getCreateMageObjectAttribute(spell.getCard(), game).getSubtype().clear();
                    } else {
                        spell.setFaceDown(false, game);
                    }
                }
            }
        }
        if (ability.getAbilityType() == AbilityType.PLAY_LAND) {
            Player player = game.getPlayer(ability.getControllerId());
            if (player != null) {
                this.resetMorph();
                if (alternateCosts.canPay(ability, this, ability.getControllerId(), game)) {
                    if (player.chooseUse(Outcome.Benefit, "Cast this card as a 2/2 "
                            + "face-down creature for " + getCosts().getText() + " ?", ability, game)) {
                        activateMorph(game);
                        // change mana costs
                        ability.getManaCostsToPay().clear();
                        ability.getCosts().clear();
                        for (Iterator it = this.alternateCosts.iterator(); it.hasNext();) {
                            Cost cost = (Cost) it.next();
                            if (cost instanceof ManaCost) {
                                ability.getManaCostsToPay().add((ManaCost) cost.copy());
                            } else {
                                ability.getCosts().add(cost.copy());
                            }
                        }
                    }
                }
            }
        }
        return isActivated(ability, game);
    }

    private void activateMorph(Game game) {
        alternateCosts.activate();
        // remember zone change counter
        if (zoneChangeCounter == 0) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                zoneChangeCounter = card.getZoneChangeCounter(game);
            } else {
                throw new IllegalArgumentException("Morph source card not found");
            }
        }
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return ruleText;
    }

    @Override
    public String getCastMessageSuffix(Game game) {
        return alternateCosts.getCastSuffixMessage(0);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public Costs<Cost> getCosts() {
        return alternateCosts;
    }

    public static void setPermanentToFaceDownCreature(MageObject mageObject, Game game) {
        mageObject.getPower().modifyBaseValue(2);
        mageObject.getToughness().modifyBaseValue(2);
        mageObject.getAbilities().clear();
        mageObject.getColor(game).setColor(new ObjectColor());
        mageObject.setName("");
        mageObject.removeAllCardTypes(game);
        mageObject.addCardType(game, CardType.CREATURE);
        mageObject.removeAllSubTypes(game);
        mageObject.getSuperType().clear();
        mageObject.getManaCost().clear();
        if (mageObject instanceof Permanent) {
            ((Permanent) mageObject).setExpansionSetCode("");
            ((Permanent) mageObject).setRarity(Rarity.SPECIAL);
        }

    }
}
