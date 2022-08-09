package mage.cards.t;

import mage.ObjectColor;
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
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class ThoughtPrison extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell cast");

    public ThoughtPrison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Imprint - When Thought Prison enters the battlefield, you may have target player reveal their hand. If you do, choose a nonland card from it and exile that card.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ThoughtPrisonImprintEffect(), true);
        ability.addTarget(new TargetPlayer());
        ability.setAbilityWord(AbilityWord.IMPRINT);
        this.addAbility(ability);

        // Whenever a player casts a spell that shares a color or converted mana cost with the exiled card, Thought Prison deals 2 damage to that player.
        this.addAbility(new ThoughtPrisonTriggeredAbility());
    }

    private ThoughtPrison(final ThoughtPrison card) {
        super(card);
    }

    @Override
    public ThoughtPrison copy() {
        return new ThoughtPrison(this);
    }
}

class ThoughtPrisonImprintEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("nonland card");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public ThoughtPrisonImprintEffect() {
        super(Outcome.Benefit);
        staticText = "exile a nonland card from target player's hand";
    }

    public ThoughtPrisonImprintEffect(ThoughtPrisonImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());

        if (player != null && targetPlayer != null) {
            targetPlayer.revealCards("Thought Prison ", targetPlayer.getHand(), game);

            TargetCard target = new TargetCard(1, Zone.HAND, filter);
            if (player.choose(Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = targetPlayer.getHand().get(targetId, game);
                    if (card != null) {
                        card.moveToExile(source.getSourceId(), "Thought Prison", source, game);
                        Permanent permanent = game.getPermanent(source.getSourceId());
                        if (permanent != null) {
                            permanent.imprint(card.getId(), game);
                            permanent.addInfo("imprint", "[Exiled card - " + card.getName() + ']', game);
                        }
                        return true;
                    }
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public ThoughtPrisonImprintEffect copy() {
        return new ThoughtPrisonImprintEffect(this);
    }
}

class ThoughtPrisonTriggeredAbility extends TriggeredAbilityImpl {

    public ThoughtPrisonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ThoughtPrisonDamageEffect(), false);
        setTriggerPhrase("Whenever a player casts a spell that shares a color or mana value with the exiled card, ");
    }

    public ThoughtPrisonTriggeredAbility(final ThoughtPrisonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThoughtPrisonTriggeredAbility copy() {
        return new ThoughtPrisonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = (Spell) game.getObject(event.getTargetId());
        Permanent sourcePermanent = game.getPermanent(this.getSourceId());
        if (spell == null) {
            return false;
        }

        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(event.getSourceId(), Zone.BATTLEFIELD);
        }
        if (sourcePermanent == null || sourcePermanent.getImprinted() == null || sourcePermanent.getImprinted().isEmpty()) {
            return false;
        }

        Card imprintedCard = game.getCard(sourcePermanent.getImprinted().get(0));
        if (imprintedCard == null || game.getState().getZone(imprintedCard.getId()) != Zone.EXILED) {
            return false;
        }
        // Check if spell's color matches the imprinted card
        ObjectColor spellColor = spell.getColor(game);
        ObjectColor imprintedColor = imprintedCard.getColor(game);
        boolean matches = false;
        if (spellColor.shares(imprintedColor)) {
            matches = true;
        }

        // Check if spell's CMC matches the imprinted card
        int cmc = spell.getManaValue();
        int imprintedCmc = imprintedCard.getManaValue();
        if (cmc == imprintedCmc) {
            matches = true;
        }

        if (matches) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
        }
        return matches;
    }
}

class ThoughtPrisonDamageEffect extends OneShotEffect {

    public ThoughtPrisonDamageEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to that player";
    }

    public ThoughtPrisonDamageEffect(final ThoughtPrisonDamageEffect effect) {
        super(effect);
    }

    @Override
    public ThoughtPrisonDamageEffect copy() {
        return new ThoughtPrisonDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            targetPlayer.damage(2, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }
}
