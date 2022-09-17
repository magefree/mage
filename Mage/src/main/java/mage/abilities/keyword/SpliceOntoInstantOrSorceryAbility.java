package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.SpliceCardEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.Iterator;

/**
 * 702.45. Splice
 * <p>
 * 702.45a Splice is a static ability that functions while a card is in your
 * hand. "Splice onto [subtype] [cost]" means "You may reveal this card from
 * your hand as you cast a [subtype] spell. If you do, copy this card's text box
 * onto that spell and pay [cost] as an additional cost to cast that spell."
 * Paying a card's splice cost follows the rules for paying additional costs in
 * rules 601.2b and 601.2e-g.
 * <p>
 * Example: Since the card with splice remains in the player's hand, it can
 * later be cast normally or spliced onto another spell. It can even be
 * discarded to pay a "discard a card" cost of the spell it's spliced onto.
 * <p>
 * 702.45b You can't choose to use a splice ability if you can't make the
 * required choices (targets, etc.) for that card's instructions. You can't
 * splice any one card onto the same spell more than once. If you're splicing
 * more than one card onto a spell, reveal them all at once and choose the order
 * in which their instructions will be followed. The instructions on the main
 * spell have to be followed first.
 * <p>
 * 702.45c The spell has the characteristics of the main spell, plus the text
 * boxes of each of the spliced cards. The spell doesn't gain any other
 * characteristics (name, mana cost, color, supertypes, card types, subtypes,
 * etc.) of the spliced cards. Text copied onto the spell that refers to a card
 * by name refers to the spell on the stack, not the card from which the text
 * was copied.
 * <p>
 * Example: Glacial Ray is a red card with splice onto Arcane that reads,
 * "Glacial Ray deals 2 damage to any target." Suppose Glacial Ray is spliced
 * onto Reach Through Mists, a blue spell. The spell is still blue, and Reach
 * Through Mists deals the damage. This means that the ability can target a
 * creature with protection from red and deal 2 damage to that creature.
 * <p>
 * 702.45d Choose targets for the added text normally (see rule 601.2c). Note
 * that a spell with one or more targets will be countered if all of its targets
 * are illegal on resolution.
 * <p>
 * 702.45e The spell loses any splice changes once it leaves the stack (for
 * example, when it's countered, it's exiled, or it resolves).
 * <p>
 * Rulings
 * <p>
 * You must reveal all of the cards you intend to splice at the same time. Each
 * individual card can only be spliced once onto a spell. If you have more than
 * one card with the same name in your hand, you may splice both of them onto
 * the spell. A card with a splice ability can't be spliced onto itself because
 * the spell is on the stack (and not in your hand) when you reveal the cards
 * you want to splice onto it. The target for a card that's spliced onto a spell
 * may be the same as the target chosen for the original spell or for another
 * spliced-on card. (A recent change to the targeting rules allows this, but
 * most other cards are unaffected by the change.) If you splice a targeted card
 * onto an untargeted spell, the entire spell will be countered if the target
 * isn't legal when the spell resolves. If you splice an untargeted card onto a
 * targeted spell, the entire spell will be countered if the target isn't legal
 * when the spell resolves. A spell is countered on resolution only if *all* of
 * its targets are illegal (or the spell is countered by an effect).
 *
 * @author LevelX2
 */
public class SpliceOntoInstantOrSorceryAbility extends SimpleStaticAbility {

    private static final String KEYWORD_TEXT = "Splice onto instant or sorcery";
    private Costs<Cost> spliceCosts = new CostsImpl<>();
    private boolean nonManaCosts = false;

    public SpliceOntoInstantOrSorceryAbility(String manaString) {
        super(Zone.HAND, new SpliceOntoInstantOrSorceryEffect());
        spliceCosts.add(new ManaCostsImpl<>(manaString));
    }

    public SpliceOntoInstantOrSorceryAbility(Cost cost) {
        super(Zone.HAND, new SpliceOntoInstantOrSorceryEffect());
        spliceCosts.add(cost);
        nonManaCosts = true;
    }

    private SpliceOntoInstantOrSorceryAbility(final SpliceOntoInstantOrSorceryAbility ability) {
        super(ability);
        this.spliceCosts = ability.spliceCosts.copy();
        this.nonManaCosts = ability.nonManaCosts;
    }

    @Override
    public SimpleStaticAbility copy() {
        return new SpliceOntoInstantOrSorceryAbility(this);
    }

    Costs getSpliceCosts() {
        return spliceCosts;
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        sb.append(KEYWORD_TEXT).append(nonManaCosts ? "&mdash;" : " ");
        sb.append(spliceCosts.getText()).append(nonManaCosts ? ". " : " ");
        sb.append("<i>(As you cast an instant or sorcery spell, you may reveal this card from your hand and pay its splice cost. If you do, add this card's effects to that spell.)</i>");
        return sb.toString();
    }
}

class SpliceOntoInstantOrSorceryEffect extends SpliceCardEffectImpl {

    SpliceOntoInstantOrSorceryEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Copy);
        staticText = "Splice onto Instant or Sorcery";
    }

    private SpliceOntoInstantOrSorceryEffect(final SpliceOntoInstantOrSorceryEffect effect) {
        super(effect);
    }

    @Override
    public SpliceOntoInstantOrSorceryEffect copy() {
        return new SpliceOntoInstantOrSorceryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(source.getControllerId());
        Card spliceCard = game.getCard(source.getSourceId());
        if (spliceCard != null && controller != null) {
            Spell spell = game.getStack().getSpell(abilityToModify.getId());
            if (spell != null) {
                SpellAbility splicedAbility = spliceCard.getSpellAbility().copy();
                splicedAbility.setSpellAbilityType(SpellAbilityType.SPLICE);
                splicedAbility.setSourceId(abilityToModify.getSourceId());
                spell.addSpellAbility(splicedAbility);
                for (Iterator it = ((SpliceOntoInstantOrSorceryAbility) source).getSpliceCosts().iterator(); it.hasNext(); ) {
                    Cost cost = (Cost) it.next();
                    if (cost instanceof ManaCost) {
                        spell.getSpellAbility().getManaCostsToPay().add((ManaCost) cost.copy());
                    } else {
                        spell.getSpellAbility().getCosts().add(cost.copy());
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        MageObject object = game.getObject(abilityToModify.getSourceId());
        if (object != null && object.isInstantOrSorcery(game)) {
            return spliceSpellCanBeActivated(source, game);
        }
        return false;
    }

    private boolean spliceSpellCanBeActivated(Ability source, Game game) {
        // check if spell can be activated (protection problem not solved because effect will be used from the base spell?)
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            if (card.getManaCost().isEmpty()) { // e.g. Evermind
                return card.getSpellAbility().spellCanBeActivatedRegularlyNow(source.getControllerId(), game);
            } else {
                return card.getSpellAbility().canActivate(source.getControllerId(), game).canActivate();
            }
        }
        return false;
    }
}
