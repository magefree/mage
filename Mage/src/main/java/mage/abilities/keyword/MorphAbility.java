package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect.FaceDownType;
import mage.cards.Card;
import mage.constants.SpellAbilityCastMode;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;

/**
 * Morph and Megamorph
 * <p>
 * 702.36. Morph
 * <p>
 * 702.36a Morph is a static ability that functions in any zone from which you
 * could play the card it's on, and the morph effect works any time the card is
 * face down. "Morph [cost]" means "You may cast this card as a 2/2 face-down
 * creature, with no text, no name, no subtypes, and no mana cost by paying {3}
 * rather than paying its mana cost." (See rule 707, "Face-Down Spells and
 * Permanents.")
 * <p>
 * 702.36b To cast a card using its morph ability, turn it face down. It becomes
 * a 2/2 face-down creature card, with no text, no name, no subtypes, and no
 * mana cost. Any effects or prohibitions that would apply to casting a card
 * with these characteristics (and not the face-up card's characteristics) are
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
 * To do this, show all players what the permanent's morph cost would be if it
 * were face up, pay that cost, then turn the permanent face up. (If the
 * permanent wouldn't have a morph cost if it were face up, it can't be turned
 * face up this way.) The morph effect on it ends, and it regains its normal
 * characteristics. Any abilities relating to the permanent entering the
 * battlefield don't trigger when it's turned face up and don't have any effect,
 * because the permanent has already entered the battlefield.
 * <p>
 * 702.36e See rule 707, "Face-Down Spells and Permanents," for more information
 * on how to cast cards with morph.
 *
 * @author LevelX2, JayDi85
 */
public class MorphAbility extends SpellAbility {

    protected static final String ABILITY_KEYWORD = "Morph";
    protected static final String REMINDER_TEXT = "You may cast this card face down as a "
            + "2/2 creature for {3}. Turn it face up any time for its morph cost.";

    protected static final String ABILITY_KEYWORD_MEGA = "Megamorph";
    protected static final String REMINDER_TEXT_MEGA = "You may cast this card face down "
            + "as a 2/2 creature for {3}. Turn it face up any time for its megamorph "
            + "cost and put a +1/+1 counter on it.";
    protected Costs<Cost> morphCosts;

    public MorphAbility(Card card, Cost morphCost) {
        this(card, morphCost, false);
    }

    public MorphAbility(Card card, Cost morphCost, boolean useMegamorph) {
        super(new GenericManaCost(3), card.getName());
        this.timing = TimingRule.SORCERY;
        this.morphCosts = new CostsImpl<>();
        this.morphCosts.add(morphCost);
        this.setSpellAbilityCastMode(useMegamorph ? SpellAbilityCastMode.MEGAMORPH : SpellAbilityCastMode.MORPH);
        this.setSpellAbilityType(SpellAbilityType.BASE_ALTERNATE);

        // face down effect (hidden by default, visible in face down objects)
        Ability ability = new SimpleStaticAbility(new BecomesFaceDownCreatureEffect(
                this.morphCosts, (useMegamorph ? FaceDownType.MEGAMORPHED : FaceDownType.MORPHED)));
        ability.setWorksFaceDown(true);
        ability.setRuleVisible(false);
        addSubAbility(ability);
    }

    protected MorphAbility(final MorphAbility ability) {
        super(ability);
        this.morphCosts = ability.morphCosts; // can't be changed TODO: looks buggy, need research
    }

    @Override
    public MorphAbility copy() {
        return new MorphAbility(this);
    }

    public Costs<Cost> getFaceUpCosts() {
        return this.morphCosts;
    }

    @Override
    public String getRule() {
        boolean isMana = morphCosts.get(0) instanceof ManaCost;
        String text;
        String reminder;
        switch (this.getSpellAbilityCastMode()) {
            case MORPH:
                text = ABILITY_KEYWORD;
                reminder = REMINDER_TEXT;
                break;
            case MEGAMORPH:
                text = ABILITY_KEYWORD_MEGA;
                reminder = REMINDER_TEXT_MEGA;
                break;
            default:
                throw new IllegalArgumentException("Un-supported spell ability cast mode for morph: " + this.getSpellAbilityCastMode());
        }
        return text + (isMana ? " " : "&mdash;") + morphCosts.getText() + (isMana ? ' ' : ". ") + " <i>(" + reminder + ")</i>";
    }
}
