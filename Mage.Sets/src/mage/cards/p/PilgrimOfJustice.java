package mage.cards.p;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.target.TargetSource;

import java.util.UUID;

/**
 * @author cbt33, Plopman (Circle of Protection: Red)
 */
public final class PilgrimOfJustice extends CardImpl {

    public PilgrimOfJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        // {W}, Sacrifice Pilgrim of Justice: The next time a red source of your choice would deal damage this turn, prevent that damage.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PilgrimOfJusticeEffect(), new ManaCostsImpl<>("{W}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private PilgrimOfJustice(final PilgrimOfJustice card) {
        super(card);
    }

    @Override
    public PilgrimOfJustice copy() {
        return new PilgrimOfJustice(this);
    }
}

class PilgrimOfJusticeEffect extends PreventionEffectImpl {

    private static final FilterObject filter = new FilterObject("red source");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    private TargetSource target;

    public PilgrimOfJusticeEffect() {
        super(Duration.EndOfTurn);
        target = new TargetSource(filter);

        staticText = "The next time a red source of your choice would deal damage to you this turn, prevent that damage";
    }

    public PilgrimOfJusticeEffect(final PilgrimOfJusticeEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public PilgrimOfJusticeEffect copy() {
        return new PilgrimOfJusticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), event.getAmount(), ((DamageEvent) event).isCombatDamage());
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            this.used = true; // one time use
            game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), damage));
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            return event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(target.getFirstTarget());
        }
        return false;
    }

}
