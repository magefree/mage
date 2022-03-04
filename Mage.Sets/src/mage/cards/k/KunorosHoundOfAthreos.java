package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KunorosHoundOfAthreos extends CardImpl {

    public KunorosHoundOfAthreos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Creature cards in graveyards can't enter the battlefield.
        this.addAbility(new SimpleStaticAbility(new KunorosHoundOfAthreosEnterEffect()));

        // Players can't cast spells from graveyards.
        this.addAbility(new SimpleStaticAbility(new KunorosHoundOfAthreosCastEffect()));
    }

    private KunorosHoundOfAthreos(final KunorosHoundOfAthreos card) {
        super(card);
    }

    @Override
    public KunorosHoundOfAthreos copy() {
        return new KunorosHoundOfAthreos(this);
    }
}

class KunorosHoundOfAthreosEnterEffect extends ContinuousRuleModifyingEffectImpl {

    KunorosHoundOfAthreosEnterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "creature cards in graveyards can't enter the battlefield";
    }

    private KunorosHoundOfAthreosEnterEffect(final KunorosHoundOfAthreosEnterEffect effect) {
        super(effect);
    }

    @Override
    public KunorosHoundOfAthreosEnterEffect copy() {
        return new KunorosHoundOfAthreosEnterEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() != Zone.BATTLEFIELD
                || zEvent.getFromZone() != Zone.GRAVEYARD) {
            return false;
        }
        Card card = game.getCard(zEvent.getTargetId());
        return card != null && card.isCreature(game);
    }
}

class KunorosHoundOfAthreosCastEffect extends ContinuousRuleModifyingEffectImpl {

    KunorosHoundOfAthreosCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "players can't cast spells from graveyards";
    }

    private KunorosHoundOfAthreosCastEffect(final KunorosHoundOfAthreosCastEffect effect) {
        super(effect);
    }

    @Override
    public KunorosHoundOfAthreosCastEffect copy() {
        return new KunorosHoundOfAthreosCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getCard(event.getSourceId()) != null
                && game.getState().getZone(event.getSourceId()) == Zone.GRAVEYARD;
    }
}
