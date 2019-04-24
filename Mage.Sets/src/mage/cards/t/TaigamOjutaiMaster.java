
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.AttackedThisTurnSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.UUID;

/**
 *
 * @author spjspj
 */
public final class TaigamOjutaiMaster extends CardImpl {

    private static final String effectText = "Whenever you cast an instant or sorcery spell from your hand, if {this} attacked this turn, that spell gains rebound.";
    private static final FilterSpell filter = new FilterSpell("Instant, Sorcery, and Dragon spells");

    static {
        filter.add(
                (Predicates.or(
                        new CardTypePredicate(CardType.INSTANT),
                        new CardTypePredicate(CardType.SORCERY),
                        new SubtypePredicate(SubType.DRAGON)))
        );
    }

    public TaigamOjutaiMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Instant, sorcery, and Dragon spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeCounteredControlledEffect(filter, new FilterStackObject(), Duration.WhileOnBattlefield)));
        
        // Whenever you cast an instant or sorcery spell from your hand, if Taigam, Ojutai Master attacked this turn, that spell gains rebound.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new TaigamOjutaiMasterTriggeredAbility(),
                AttackedThisTurnSourceCondition.instance,
                effectText);
        this.addAbility(ability, new AttackedThisTurnWatcher());
    }

    public TaigamOjutaiMaster(final TaigamOjutaiMaster card) {
        super(card);
    }

    @Override
    public TaigamOjutaiMaster copy() {
        return new TaigamOjutaiMaster(this);
    }
}

class TaigamOjutaiMasterTriggeredAbility extends DelayedTriggeredAbility {

    public TaigamOjutaiMasterTriggeredAbility() {
        super(new TaigamOjutaiMasterGainReboundEffect(), Duration.EndOfTurn, true);
    }

    private TaigamOjutaiMasterTriggeredAbility(final TaigamOjutaiMasterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TaigamOjutaiMasterTriggeredAbility copy() {
        return new TaigamOjutaiMasterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.getFromZone() == Zone.HAND) {
                if (spell.getCard() != null
                        && spell.getCard().isInstant() || spell.getCard().isSorcery()) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(spell.getId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell from your hand, if {this} attacked this turn, " + super.getRule();

    }
}

class TaigamOjutaiMasterGainReboundEffect extends ContinuousEffectImpl {

    public TaigamOjutaiMasterGainReboundEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "that spell gains rebound";
    }

    public TaigamOjutaiMasterGainReboundEffect(final TaigamOjutaiMasterGainReboundEffect effect) {
        super(effect);
    }

    @Override
    public TaigamOjutaiMasterGainReboundEffect copy() {
        return new TaigamOjutaiMasterGainReboundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
            if (spell != null) {
                Card card = spell.getCard();
                if (card != null) {
                    addReboundAbility(card, source, game);
                }
            } else {
                discard();
            }
            return true;
        }
        return false;
    }

    private void addReboundAbility(Card card, Ability source, Game game) {
        boolean found = card.getAbilities().stream().anyMatch(ability -> ability instanceof ReboundAbility);
        if (!found) {
            Ability ability = new ReboundAbility();
            game.getState().addOtherAbility(card, ability);
        }
    }
}
