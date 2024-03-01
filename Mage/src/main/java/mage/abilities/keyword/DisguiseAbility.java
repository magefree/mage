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
import mage.cards.Card;
import mage.constants.SpellAbilityCastMode;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;

/**
 * 702.168. Disguise
 * <p>
 * 702.168a
 * Disguise is a static ability that functions in any zone from which you could play the card it’s on,
 * and the disguise effect works any time the card is face down. “Disguise [cost]” means “You may cast this
 * card as a 2/2 face-down creature with ward {2}, no name, no subtypes, and no mana cost by paying {3} rather
 * than paying its mana cost.” (See rule 708, “Face-Down Spells and Permanents.”)
 * <p>
 * 702.168b
 * To cast a card using its disguise ability, turn the card face down and announce that you are using a disguise ability.
 * It becomes a 2/2 face-down creature card with ward {2}, no name, no subtypes, and no mana cost. Any effects or
 * prohibitions that would apply to casting a card with these characteristics (and not the face-up card’s characteristics)
 * are applied to casting this card. These values are the copiable values of that object’s characteristics.
 * (See rule 613, “Interaction of Continuous Effects,” and rule 707, “Copying Objects.”) Put it onto the stack
 * (as a face-down spell with the same characteristics), and pay {3} rather than pay its mana cost. This follows the
 * rules for paying alternative costs. You can use a disguise ability to cast a card from any zone from which you
 * could normally cast it. When the spell resolves, it enters the battlefield with the same characteristics the spell
 * had. The disguise effect applies to the face-down object wherever it is, and it ends when the permanent is turned
 * face up.
 * <p>
 * 702.168c
 * You can’t normally cast a card face down. A disguise ability allows you to do so.
 * <p>
 * 702.168d
 * Any time you have priority, you may turn a face-down permanent you control with a disguise ability face up.
 * This is a special action; it doesn’t use the stack (see rule 116). To do this, show all players what the
 * permanent’s disguise cost would be if it were face up, pay that cost, then turn the permanent face up.
 * (If the permanent wouldn’t have a disguise cost if it were face up, it can’t be turned face up this way.)
 * The disguise effect on it ends, and it regains its normal characteristics. Any abilities relating to the
 * permanent entering the battlefield don’t trigger when it’s turned face up and don’t have any effect,
 * because the permanent has already entered the battlefield.
 * <p>
 * 702.168e
 * If a permanent’s disguise cost includes X, other abilities of that permanent may also refer to X.
 * The value of X in those abilities is equal to the value of X chosen as the disguise special action was taken.
 * <p>
 * 702.168f
 * See rule 708, “Face-Down Spells and Permanents,” for more information about how to cast cards with a disguise ability.
 * <p>
 * <p>
 * MorphAbility as a reference implementation
 *
 * @author JayDi85
 */
public class DisguiseAbility extends SpellAbility {

    protected static final String ABILITY_KEYWORD = "Disguise";
    protected static final String REMINDER_TEXT = "You may cast this card face down for {3} as a 2/2 creature with "
            + "ward {2}. Turn it face up any time for its disguise cost.";

    protected Costs<Cost> disguiseCosts;

    public DisguiseAbility(Card card, Cost disguiseCost) {
        super(new GenericManaCost(3), card.getName());
        this.timing = TimingRule.SORCERY;
        this.disguiseCosts = new CostsImpl<>();
        this.disguiseCosts.add(disguiseCost);
        this.setSpellAbilityCastMode(SpellAbilityCastMode.DISGUISE);
        this.setSpellAbilityType(SpellAbilityType.BASE_ALTERNATE);

        // face down effect (hidden by default, visible in face down objects)
        Ability ability = new SimpleStaticAbility(new BecomesFaceDownCreatureEffect(
                this.disguiseCosts, BecomesFaceDownCreatureEffect.FaceDownType.DISGUISED));
        ability.setWorksFaceDown(true);
        ability.setRuleVisible(false);
        addSubAbility(ability);
    }

    private DisguiseAbility(final DisguiseAbility ability) {
        super(ability);
        this.disguiseCosts = ability.disguiseCosts; // can't be changed TODO: looks buggy, need research
    }

    @Override
    public DisguiseAbility copy() {
        return new DisguiseAbility(this);
    }

    public Costs<Cost> getFaceUpCosts() {
        return this.disguiseCosts;
    }

    @Override
    public String getRule() {
        boolean isMana = disguiseCosts.get(0) instanceof ManaCost;
        return ABILITY_KEYWORD + (isMana ? " " : "&mdash;")
                + this.disguiseCosts.getText()
                + (isMana ? ' ' : ". ")
                + " <i>(" + REMINDER_TEXT + ")</i>";
    }
}
