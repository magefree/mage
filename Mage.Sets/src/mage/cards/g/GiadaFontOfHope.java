package mage.cards.g;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author woshikie
 */
public final class GiadaFontOfHope extends CardImpl {
    private static final FilterSpell ANGEL_SPELL_FILTER = new FilterSpell("an Angel spell");

    static {
        ANGEL_SPELL_FILTER.add(SubType.ANGEL.getPredicate());
    }

    public GiadaFontOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Each other Angel you control enters the battlefield with an additional +1/+1 counter on it for each Angel you already control.
        this.addAbility(
                new SimpleStaticAbility(
                        Zone.BATTLEFIELD,
                        new GiadaFontOfHopeEntersBattlefieldEffect()
                )
        );

        // {T}: Add {W}. Spend this mana only to cast an Angel spell.
        this.addAbility(
                new ConditionalColoredManaAbility(
                        Mana.WhiteMana(1),
                        new ConditionalSpellManaBuilder(ANGEL_SPELL_FILTER)
                )

        );
    }

    private GiadaFontOfHope(final GiadaFontOfHope card) {
        super(card);
    }

    @Override
    public GiadaFontOfHope copy() {
        return new GiadaFontOfHope(this);
    }
}


class GiadaFontOfHopeEntersBattlefieldEffect extends ReplacementEffectImpl {

    public GiadaFontOfHopeEntersBattlefieldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Each other Angel you control enters the battlefield with an additional +1/+1 counter on it for each Angel you already control.";
    }

    private GiadaFontOfHopeEntersBattlefieldEffect(GiadaFontOfHopeEntersBattlefieldEffect effect) {
        super(effect);
    }


    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null) {
            int amount =
                    (int) game.getBattlefield().getAllActivePermanents().stream().filter(perm -> {
                        return perm.hasSubtype(SubType.ANGEL, game) // perm is Angel
                                && perm.isControlledBy(source.getControllerId()); // perm is Controlled by player
                    }).count();
            if (amount > 0)
                permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
        }
        return false;

    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && permanent.hasSubtype(SubType.ANGEL, game)
                && !event.getTargetId().equals(source.getSourceId());

    }

    @Override
    public GiadaFontOfHopeEntersBattlefieldEffect copy() {
        return new GiadaFontOfHopeEntersBattlefieldEffect(this);
    }
}