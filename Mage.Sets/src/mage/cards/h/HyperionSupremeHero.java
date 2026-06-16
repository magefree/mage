package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class HyperionSupremeHero extends CardImpl {

    public HyperionSupremeHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ETERNAL);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If a source would deal damage to you or a Hero you control, prevent all but 1 of that damage.
        this.addAbility(new SimpleStaticAbility(new HyperionSupremeHeroEffect()));
    }

    private HyperionSupremeHero(final HyperionSupremeHero card) {
        super(card);
    }

    @Override
    public HyperionSupremeHero copy() {
        return new HyperionSupremeHero(this);
    }
}

class HyperionSupremeHeroEffect extends PreventionEffectImpl {

    public HyperionSupremeHeroEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "If a source would deal damage to you or a Hero you control, prevent all but 1 of that damage";
        consumable = false;
    }

    public HyperionSupremeHeroEffect(HyperionSupremeHeroEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        if (damage > 1) {
            amountToPrevent = damage - 1;
            preventDamageAction(event, source, game);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                && event.getTargetId().equals(source.getControllerId())) {
            return super.applies(event, source, game);
        }

        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.hasSubtype(SubType.HERO, game) && permanent.isControlledBy(source.getControllerId())) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public HyperionSupremeHeroEffect copy() {
        return new HyperionSupremeHeroEffect(this);
    }
}
