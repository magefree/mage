package mage.abilities.keyword;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AlternativeSourceCostsImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect.FaceDownType;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 * 702.36. Morph
 * <p>
 * 702.36a Morph is a static ability that functions in any zone from which you
 * could play the card its on, and the morph effect works any time the card is
 * face down. "Morph [cost]" means "You may cast this card as a 2/2 face-down
 * creature, with no text, no name, no subtypes, and no mana cost by paying {3}
 * rather than paying its mana cost." (See rule 707, "Face-Down Spells and
 * Permanents.")
 * <p>
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
 * <p>
 * 702.36c You can't cast a card face down if it doesn't have morph.
 * <p>
 * 702.36d If you have priority, you may turn a face-down permanent you control
 * face up. This is a special action; it doesn't use the stack (see rule 115).
 * To do this, show all players what the permanents morph cost would be if it
 * were face up, pay that cost, then turn the permanent face up. (If the
 * permanent wouldn't have a morph cost if it were face up, it cant be turned
 * face up this way.) The morph effect on it ends, and it regains its normal
 * characteristics. Any abilities relating to the permanent entering the
 * battlefield dont trigger when its turned face up and dont have any effect,
 * because the permanent has already entered the battlefield.
 * <p>
 * 702.36e See rule 707, "Face-Down Spells and Permanents," for more information
 * on how to cast cards with morph.
 *
 * @author LevelX2
 */
public class MorphAbility extends AlternativeSourceCostsImpl {

    protected static final String ABILITY_KEYWORD = "Morph";
    protected static final String ABILITY_KEYWORD_MEGA = "Megamorph";
    protected static final String REMINDER_TEXT = "You may cast this card face down as a "
            + "2/2 creature for {3}. Turn it face up any time for its morph cost.";
    protected static final String REMINDER_TEXT_MEGA = "You may cast this card face down "
            + "as a 2/2 creature for {3}. Turn it face up any time for its megamorph "
            + "cost and put a +1/+1 counter on it.";
    protected Costs<Cost> morphCosts;
    // needed to check activation status, if card changes zone after casting it
    private final boolean megamorph;

    public MorphAbility(Cost morphCost) {
        this(morphCost, false);
    }

    public MorphAbility(Cost morphCost, boolean megamorph) {
        super(megamorph ? ABILITY_KEYWORD_MEGA : ABILITY_KEYWORD, megamorph ? REMINDER_TEXT_MEGA : REMINDER_TEXT, new GenericManaCost(3));
        this.morphCosts = new CostsImpl<>();
        this.morphCosts.add(morphCost);
        this.megamorph = megamorph;
        this.setWorksFaceDown(true);
        Ability ability = new SimpleStaticAbility(new BecomesFaceDownCreatureEffect(
                morphCosts, (megamorph ? FaceDownType.MEGAMORPHED : FaceDownType.MORPHED)));
        ability.setWorksFaceDown(true);
        ability.setRuleVisible(false);
        addSubAbility(ability);
    }

    public MorphAbility(final MorphAbility ability) {
        super(ability);
        this.morphCosts = ability.morphCosts; // can't be changed
        this.megamorph = ability.megamorph;
    }

    @Override
    public MorphAbility copy() {
        return new MorphAbility(this);
    }

    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        switch (ability.getAbilityType()) {
            case SPELL:
                Spell spell = game.getStack().getSpell(ability.getId());
                if (spell != null) {
                    spell.setFaceDown(true, game);
                    if (handleActivatingAlternativeCosts(ability, game)) {
                        game.getState().setValue("MorphAbility" + ability.getSourceId(), "activated");
                        spell.getColor(game).setColor(null);
                        game.getState().getCreateMageObjectAttribute(spell.getCard(), game).getSubtype().clear();
                    } else {
                        spell.setFaceDown(false, game);
                    }
                }
                break;
            case PLAY_LAND:
                handleActivatingAlternativeCosts(ability, game);
        }
        return isActivated(ability, game);
    }

    public Costs<Cost> getMorphCosts() {
        return morphCosts;
    }

    @Override
    public String getRule() {
        boolean isMana = morphCosts.get(0) instanceof ManaCost;
        return alternativeCost.getName() + (isMana ? " " : "&mdash;") +
                morphCosts.getText() + (isMana ? ' ' : ". ") + alternativeCost.getReminderText();
    }

    /**
     * Hide all info and make it a 2/2 creature
     *
     * @param targetObject
     * @param sourcePermanent source of the face down status
     * @param game
     */
    public static void setPermanentToFaceDownCreature(MageObject targetObject, Permanent sourcePermanent, Game game) {
        targetObject.getPower().setModifiedBaseValue(2);
        targetObject.getToughness().setModifiedBaseValue(2);
        targetObject.getAbilities().clear();
        targetObject.getColor(game).setColor(new ObjectColor());
        targetObject.setName("");
        targetObject.removeAllCardTypes(game);
        targetObject.addCardType(game, CardType.CREATURE);
        targetObject.removeAllSubTypes(game);
        targetObject.removeAllSuperTypes(game);
        targetObject.getManaCost().clear();

        Token emptyImage = new EmptyToken();

        // TODO: add morph image here?
        if (targetObject instanceof Permanent) {
            // hide image info
            CardUtil.copySetAndCardNumber(targetObject, emptyImage);
            // hide rarity info
            ((Permanent) targetObject).setRarity(Rarity.SPECIAL);
        } else if (targetObject instanceof Token) {
            CardUtil.copySetAndCardNumber(targetObject, emptyImage);
        } else {
            throw new IllegalArgumentException("Wrong code usage: un-supported targetObject in face down method: " + targetObject.getClass().getSimpleName());
        }
    }
}
