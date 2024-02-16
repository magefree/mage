package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.p.PakoArcaneRetriever;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.ManaPoolItem;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HaldanAvidArcanist extends CardImpl {

    public HaldanAvidArcanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Partner with Pako, Arcane Retriever
        this.addAbility(new PartnerWithAbility("Pako, Arcane Retriever"));

        // You may play noncreature cards from exile with fetch counters on them if you 
        // exiled them, and you may spend mana as though it were mana of any color to cast those spells.
        Ability ability = new SimpleStaticAbility(new HaldanAvidArcanistCastFromExileEffect());
        ability.addEffect(new HaldanAvidArcanistSpendAnyManaEffect());
        this.addAbility(ability, PakoArcaneRetriever.createWatcher());
    }

    private HaldanAvidArcanist(final HaldanAvidArcanist card) {
        super(card);
    }

    @Override
    public HaldanAvidArcanist copy() {
        return new HaldanAvidArcanist(this);
    }
}

class HaldanAvidArcanistCastFromExileEffect extends AsThoughEffectImpl {

    HaldanAvidArcanistCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may play noncreature cards from exile with fetch counters on them if you exiled them";
    }

    private HaldanAvidArcanistCastFromExileEffect(final HaldanAvidArcanistCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HaldanAvidArcanistCastFromExileEffect copy() {
        return new HaldanAvidArcanistCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(sourceId);
        if (card == null || !source.isControlledBy(affectedControllerId)
                || game.getState().getZone(sourceId) != Zone.EXILED
                || !PakoArcaneRetriever.checkWatcher(affectedControllerId, card, game)) {
            return false;
        }
        return !card.isCreature(game) && card.getCounters(game).containsKey(CounterType.FETCH);
    }
}

class HaldanAvidArcanistSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    HaldanAvidArcanistSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = ", and you may spend mana as though it were mana of any color to cast those spells";
    }

    private HaldanAvidArcanistSpendAnyManaEffect(final HaldanAvidArcanistSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HaldanAvidArcanistSpendAnyManaEffect copy() {
        return new HaldanAvidArcanistSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        // The card may be on the stack, in which case it will not have a fetch counter
        // on it. In this case, we will have to rely on the watcher to tell us whether
        // or not it is a valid card to apply the effect to.
        Zone zone;
        Card card = game.getCard(objectId);
        if (card == null || !source.isControlledBy(affectedControllerId)
                || (zone = game.getState().getZone(objectId)) != Zone.EXILED && zone != Zone.STACK
                || !PakoArcaneRetriever.checkWatcher(affectedControllerId, card, game)) {
            return false;
        }
        return !card.isCreature(game) && (card.getCounters(game).containsKey(CounterType.FETCH) || zone == Zone.STACK);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
