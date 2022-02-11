package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * 702.102. Bestow
 * <p>
 * 702.102a Bestow represents two static abilities, one that functions while the
 * card with bestow is on the stack and another that functions both while it's
 * on stack and while it's on the battlefield. "Bestow [cost]" means "You may
 * cast this card by paying [cost] rather than its mana cost." and "If you chose
 * to pay this spell's bestow cost, it becomes an Aura enchantment and gains
 * enchant creature. These effects last until one of two things happens: this
 * spell has an illegal target as it resolves and or the permanent this spell
 * becomes, becomes unattached." Paying a card's bestow cost follows the rules
 * for paying alternative costs in rules 601.2b and 601.2e-g.
 * <p>
 * 702.102b If a spell's controller chooses to pay its bestow cost, that player
 * chooses a legal target for that Aura spell as defined by its enchant creature
 * ability and rule 601.2c. See also rule 303.4.
 * <p>
 * 702.102c A spell's controller can't choose to pay its bestow cost unless that
 * player can choose a legal target for that spell after it becomes an Aura
 * spell.
 * <p>
 * 702.102d As an Aura spell with bestow begins resolving, if its target is
 * illegal, the effect making it an Aura spell ends. It continues resolving as a
 * creature spell and will be put onto the battlefield under the control of the
 * spell's controller. This is an exception to rule 608.3a.
 * <p>
 * 702.102e If an Aura with bestow is attached to an illegal object or player,
 * it becomes unattached. This is an exception to rule 704.5n.
 * <p>
 * You don't choose whether the spell is going to be an Aura spell or not until
 * the spell is already on the stack. Abilities that affect when you can cast a
 * spell, such as flash, will apply to the creature card in whatever zone you're
 * casting it from. For example, an effect that said you can cast creature
 * spells as though they have flash will allow you to cast a creature card with
 * bestow as an Aura spell anytime you could cast an instant.
 * <p>
 * On the stack, a spell with bestow is either a creature spell or an Aura
 * spell. It's never both.
 * <p>
 * Unlike other Aura spells, an Aura spell with bestow isn't countered if its
 * target is illegal as it begins to resolve. Rather, the effect making it an
 * Aura spell ends, it loses enchant creature, it returns to being an
 * enchantment creature spell, and it resolves and enters the battlefield as an
 * enchantment creature.
 * <p>
 * Unlike other Auras, an Aura with bestow isn't put into its owner's graveyard
 * if it becomes unattached. Rather, the effect making it an Aura ends, it loses
 * enchant creature, and it remains on the battlefield as an enchantment
 * creature. It can attack (and its {T} abilities can be activated, if it has
 * any) on the turn it becomes unattached if it's been under your control
 * continuously, even as an Aura, since your most recent turn began.
 * <p>
 * If a permanent with bestow enters the battlefield by any method other than
 * being cast, it will be an enchantment creature. You can't choose to pay the
 * bestow cost and have it become an Aura.
 * <p>
 * Auras attached to a creature don't become tapped when the creature becomes
 * tapped. Except in some rare cases, an Aura with bestow remains untapped when
 * it becomes unattached and becomes a creature.
 *
 * @author LevelX2
 */
public class BestowAbility extends SpellAbility {

    public BestowAbility(Card card, String manaString) {
        super(new ManaCostsImpl<>(manaString), card.getName() + " using bestow");
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.spellAbilityCastMode = SpellAbilityCastMode.BESTOW;
        this.timing = TimingRule.SORCERY;
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.addTarget(auraTarget);
        this.addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BestowEntersBattlefieldEffect());
        ability.setRuleVisible(false);
        addSubAbility(ability);
    }

    public BestowAbility(final BestowAbility ability) {
        super(ability);
    }

    @Override
    public BestowAbility copy() {
        return new BestowAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Bestow " + getManaCostsToPay().getText() + " <i>(If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)</i>";
    }

    static public void becomeCreature(Permanent permanent, Game game) {
        // permanently changes to the object
        if (permanent != null) {
            MageObject basicObject = permanent.getBasicMageObject(game);
            if (basicObject != null) {
                game.checkStateAndTriggered();  // Bug #8157
                basicObject.getSubtype().remove(SubType.AURA);
                basicObject.addCardType(CardType.CREATURE);
            }
            permanent.getSubtype().remove(SubType.AURA);
            permanent.addCardType(CardType.CREATURE);
        }
    }

    static public void becomeAura(Card card) {
        // permanently changes to the object
        if (card != null) {
            card.addSubType(SubType.AURA);
            card.removeCardType(CardType.CREATURE);
            card.addCardType(CardType.ENCHANTMENT);
        }
    }
}

class BestowEntersBattlefieldEffect extends ReplacementEffectImpl {

    public BestowEntersBattlefieldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
    }

    public BestowEntersBattlefieldEffect(final BestowEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return EventType.ENTERS_THE_BATTLEFIELD_SELF == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent bestowPermanent = game.getPermanentEntering(source.getSourceId());
        if (bestowPermanent == null || !bestowPermanent.hasSubtype(SubType.AURA, game)) {
            return false;
        }

        // change types permanently
        MageObject basicObject = bestowPermanent.getBasicMageObject(game);
        if (basicObject != null && !basicObject.getSubtype().contains(SubType.AURA)) {
            basicObject.addSubType(SubType.AURA);
            basicObject.removeCardType(CardType.CREATURE);
        }
        return false;
    }

    @Override
    public BestowEntersBattlefieldEffect copy() {
        return new BestowEntersBattlefieldEffect(this);
    }

}
