
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class Godtoucher extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 5 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public Godtoucher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GodtoucherEffect(Duration.EndOfTurn),
                new ManaCostsImpl("{1}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public Godtoucher(final Godtoucher card) {
        super(card);
    }

    @Override
    public Godtoucher copy() {
        return new Godtoucher(this);
    }
}

class GodtoucherEffect extends PreventionEffectImpl {

    public GodtoucherEffect(Duration duration) {
        super(duration);
        staticText = "Prevent all damage that would be dealt to target creature with power 5 or greater this turn";
    }

    public GodtoucherEffect(final GodtoucherEffect effect) {
        super(effect);
    }

    @Override
    public GodtoucherEffect copy() {
        return new GodtoucherEffect(Duration.EndOfTurn);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), damage));
        }
        return false;
    }

}
