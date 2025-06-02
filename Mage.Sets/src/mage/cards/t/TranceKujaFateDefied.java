package mage.cards.t;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

/**
 * @author balazskristof
 */
public final class TranceKujaFateDefied extends CardImpl {

    public TranceKujaFateDefied(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        this.color.setBlack(true);
        this.color.setRed(true);

        this.nightCard = true;

        // Flame Star -- If a Wizard you control would deal damage to a permanent or player, it deals double that damage instead.
        this.addAbility(new SimpleStaticAbility(new TranceKujaFateDefiedEffect()).withFlavorWord("Flame Star"));
    }

    private TranceKujaFateDefied(final TranceKujaFateDefied card) {
        super(card);
    }

    @Override
    public TranceKujaFateDefied copy() {
        return new TranceKujaFateDefied(this);
    }
}

class TranceKujaFateDefiedEffect extends ReplacementEffectImpl {

    TranceKujaFateDefiedEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a Wizard you control would deal damage to a permanent or player, it deals double that damage instead.";
    }

    private TranceKujaFateDefiedEffect(final TranceKujaFateDefiedEffect effect) {
        super(effect);
    }

    @Override
    public TranceKujaFateDefiedEffect copy() {
        return new TranceKujaFateDefiedEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER)
                || event.getType().equals(GameEvent.EventType.DAMAGE_PERMANENT);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getControllerId(event.getSourceId()).equals(source.getControllerId())
                && Optional.ofNullable(game.getObject(event.getSourceId()))
                .map(object -> object.hasSubtype(SubType.WIZARD, game))
                .orElse(false);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}