
package mage.cards.t;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class TheMirariConjecture extends CardImpl {

    private static final FilterCard filterInstantCard = new FilterCard("instant card from your graveyard");
    private static final FilterCard filterSorceryCard = new FilterCard("sorcery card from your graveyard");

    static {
        filterInstantCard.add(CardType.INSTANT.getPredicate());
        filterSorceryCard.add(CardType.SORCERY.getPredicate());
    }

    public TheMirariConjecture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);
        // I — Return target instant card from your graveyard to your hand.                
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new ReturnFromGraveyardToHandTargetEffect(),
                new TargetCardInYourGraveyard(filterInstantCard)
        );

        // II — Return target sorcery card from your graveyard to your hand.               
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new ReturnFromGraveyardToHandTargetEffect(),
                new TargetCardInYourGraveyard(filterSorceryCard)
        );

        // III — Until end of turn, whenever you cast an instant or sorcery spell, copy it. You may choose new targets for the copy.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new CreateDelayedTriggeredAbilityEffect(new TheMirariConjectureDelayedTriggeredAbility()));
        this.addAbility(sagaAbility);

    }

    private TheMirariConjecture(final TheMirariConjecture card) {
        super(card);
    }

    @Override
    public TheMirariConjecture copy() {
        return new TheMirariConjecture(this);
    }
}

class TheMirariConjectureDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public TheMirariConjectureDelayedTriggeredAbility() {
        super(new CopyTargetSpellEffect(true), Duration.EndOfTurn, false);
    }

    public TheMirariConjectureDelayedTriggeredAbility(final TheMirariConjectureDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheMirariConjectureDelayedTriggeredAbility copy() {
        return new TheMirariConjectureDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null
                    && spell.isInstantOrSorcery(game)) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever you cast an instant or sorcery spell, copy it. You may choose new targets for the copy.";
    }
}
