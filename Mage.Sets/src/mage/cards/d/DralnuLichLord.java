package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.GainFlashbackTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class DralnuLichLord extends CardImpl {

    public DralnuLichLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If damage would be dealt to Dralnu, Lich Lord, sacrifice that many permanents instead.
        this.addAbility(new SimpleStaticAbility(new DralnuLichLordReplacementEffect()));

        // {tap}: Target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        Ability ability = new SimpleActivatedAbility(new GainFlashbackTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
        this.addAbility(ability);
    }

    private DralnuLichLord(final DralnuLichLord card) {
        super(card);
    }

    @Override
    public DralnuLichLord copy() {
        return new DralnuLichLord(this);
    }
}

class DralnuLichLordReplacementEffect extends ReplacementEffectImpl {
    DralnuLichLordReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Sacrifice);
        staticText = "If damage would be dealt to {this}, sacrifice that many permanents instead";
    }

    private DralnuLichLordReplacementEffect(final DralnuLichLordReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        new SacrificeControllerEffect(new FilterPermanent(), damageEvent.getAmount(), "").apply(game, source);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public DralnuLichLordReplacementEffect copy() {
        return new DralnuLichLordReplacementEffect(this);
    }
}
