package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author emerald000
 */
public final class SpellweaverHelix extends CardImpl {

    private static final FilterCard filter = new FilterCard("sorcery cards");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    public SpellweaverHelix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Imprint - When Spellweaver Helix enters the battlefield, you may exile two target sorcery cards from a single graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SpellweaverHelixImprintEffect(), true);
        ability.addTarget(new TargetCardInASingleGraveyard(2, 2, filter));
        ability.setAbilityWord(AbilityWord.IMPRINT);
        this.addAbility(ability);

        // Whenever a player casts a card, if it has the same name as one of the cards exiled with Spellweaver Helix, you may copy the other. If you do, you may cast the copy without paying its mana cost.
        this.addAbility(new SpellweaverHelixTriggeredAbility());
    }

    private SpellweaverHelix(final SpellweaverHelix card) {
        super(card);
    }

    @Override
    public SpellweaverHelix copy() {
        return new SpellweaverHelix(this);
    }
}

class SpellweaverHelixImprintEffect extends OneShotEffect {

    SpellweaverHelixImprintEffect() {
        super(Outcome.Exile);
        this.staticText = "you may exile two target sorcery cards from a single graveyard";
    }

    SpellweaverHelixImprintEffect(final SpellweaverHelixImprintEffect effect) {
        super(effect);
    }

    @Override
    public SpellweaverHelixImprintEffect copy() {
        return new SpellweaverHelixImprintEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    controller.moveCardsToExile(card, source, game, true, CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), source.getSourceObject(game).getIdName());
                    if (sourcePermanent != null) {
                        sourcePermanent.imprint(targetId, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class SpellweaverHelixTriggeredAbility extends TriggeredAbilityImpl {

    SpellweaverHelixTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SpellweaverHelixCastEffect(), false);
    }

    SpellweaverHelixTriggeredAbility(final SpellweaverHelixTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpellweaverHelixTriggeredAbility copy() {
        return new SpellweaverHelixTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.getCard() != null && !spell.getCard().isCopy()) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(spell.getId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Spell spell = game.getStack().getSpell(this.getEffects().get(0).getTargetPointer().getFirst(game, this));
        if (spell != null) {
            String spellName = spell.getName();
            Permanent sourcePermanent = game.getPermanent(this.getSourceId());
            if (sourcePermanent != null) {
                for (UUID imprintId : sourcePermanent.getImprinted()) {
                    Card card = game.getCard(imprintId);
                    if (card != null && card.getName().equals(spellName)) {
                        ((SpellweaverHelixCastEffect) this.getEffects().get(0)).setSpellName(spellName);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a card, if it has the same name as one of the cards exiled with Spellweaver Helix, you may copy the other. If you do, you may cast the copy without paying its mana cost.";
    }
}

class SpellweaverHelixCastEffect extends OneShotEffect {

    private String spellName = "";

    SpellweaverHelixCastEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may copy the other. If you do, you may cast the copy without paying its mana cost";
    }

    SpellweaverHelixCastEffect(final SpellweaverHelixCastEffect effect) {
        super(effect);
        this.spellName = effect.spellName;
    }

    @Override
    public SpellweaverHelixCastEffect copy() {
        return new SpellweaverHelixCastEffect(this);
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (sourcePermanent != null) {
                boolean foundSpellWithSameName = false;
                for (UUID imprintId : sourcePermanent.getImprinted()) {
                    Card card = game.getCard(imprintId);
                    if (card != null) {
                        if (!foundSpellWithSameName && card.getName().equals(spellName)) {
                            foundSpellWithSameName = true;
                        } else {
                            if (controller.chooseUse(Outcome.Copy, "Copy " + card.getIdName(), source, game)) {
                                Card copy = game.copyCard(card, source, source.getControllerId());
                                if (controller.chooseUse(Outcome.PlayForFree, "Cast " + copy.getIdName() + " without paying its mana cost?", source, game)) {
                                    game.getState().setValue("PlayFromNotOwnHandZone" + copy.getId(), Boolean.TRUE);
                                    controller.cast(controller.chooseAbilityForCast(copy, game, true),
                                            game, true, new ApprovingObject(source, game));
                                    game.getState().setValue("PlayFromNotOwnHandZone" + copy.getId(), null);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
