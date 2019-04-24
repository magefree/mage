
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.GainAbilitySpellsEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterObject;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SoulfireGrandMaster extends CardImpl {

    private static final FilterObject filter = new FilterObject("instant and sorcery spells you control");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
    }

    public SoulfireGrandMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Instant and sorcery spells you control have lifelink.
        Effect effect = new GainAbilitySpellsEffect(LifelinkAbility.getInstance(), filter);
        effect.setText("Instant and sorcery spells you control have lifelink");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // {2}{U/R}{U/R}: The next time you cast an instant or sorcery spell from your hand this turn, put that card into your hand instead of your graveyard as it resolves.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SoulfireGrandMasterCastFromHandReplacementEffect(), new ManaCostsImpl("{2}{U/R}{U/R}")));

    }

    public SoulfireGrandMaster(final SoulfireGrandMaster card) {
        super(card);
    }

    @Override
    public SoulfireGrandMaster copy() {
        return new SoulfireGrandMaster(this);
    }
}

class SoulfireGrandMasterCastFromHandReplacementEffect extends ReplacementEffectImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
    }

    private UUID spellId;

    SoulfireGrandMasterCastFromHandReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.ReturnToHand);
        this.spellId = null;
        this.staticText = "The next time you cast an instant or sorcery spell from your hand this turn, put that card into your hand instead of into your graveyard as it resolves";
    }

    SoulfireGrandMasterCastFromHandReplacementEffect(SoulfireGrandMasterCastFromHandReplacementEffect effect) {
        super(effect);
        this.spellId = effect.spellId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public SoulfireGrandMasterCastFromHandReplacementEffect copy() {
        return new SoulfireGrandMasterCastFromHandReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject mageObject = game.getObject(spellId);
        if (!(mageObject instanceof Spell) || ((Spell) mageObject).isCopiedSpell()) {
            return false;
        } else {
            Card sourceCard = game.getCard(spellId);
            if (sourceCard != null) {
                Player player = game.getPlayer(sourceCard.getOwnerId());
                if (player != null) {
                    player.moveCards(sourceCard, Zone.HAND, source, game);
                    discard();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        //Something hit the stack from the hand, see if its a spell with this ability.
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (spellId == null
                && // because this effect works only once, spellId has to be null here
                zEvent.getFromZone() == Zone.HAND
                && zEvent.getToZone() == Zone.STACK
                && event.getPlayerId().equals(source.getControllerId())) {
            MageObject object = game.getObject(event.getTargetId());
            if (object instanceof Card) {
                if (filter.match((Card) object, game)) {
                    this.spellId = event.getTargetId();
                }
            }
        } else {
            // the spell goes to graveyard now so move it to hand again
            if (zEvent.getFromZone() == Zone.STACK
                    && zEvent.getToZone() == Zone.GRAVEYARD
                    && event.getTargetId().equals(spellId)) {
                Spell spell = game.getStack().getSpell(spellId);
                if (spell != null && !spell.isCountered()) {
                    return true;
                }
            }
        }
        return false;
    }

}
