package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SubLayer;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterMana;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author ssouders412
 */
public final class KrrikSonOfYawgmoth extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a black spell");

    static {
        filterSpell.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public KrrikSonOfYawgmoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B/P}{B/P}{B/P}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // ({B/P} can be paid with either {B} or 2 life.)
        
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // For each {B} in a cost, you may pay 2 life rather than pay that mana.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KrrikSonOfYawgmothPhyrexianEffect()));

        // Whenever you cast a black spell, put a +1/+1 counter on K'rrik, Son of Yawgmoth.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filterSpell, false));
    }

    private KrrikSonOfYawgmoth(final KrrikSonOfYawgmoth card) {
        super(card);
    }

    @Override
    public KrrikSonOfYawgmoth copy() {
        return new KrrikSonOfYawgmoth(this);
    }
}

class KrrikSonOfYawgmothPhyrexianEffect extends ContinuousEffectImpl {

    public KrrikSonOfYawgmothPhyrexianEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        this.staticText = "for each {B} in a cost, you may pay 2 life rather than pay that mana";
    }

    public KrrikSonOfYawgmothPhyrexianEffect(final KrrikSonOfYawgmothPhyrexianEffect effect) {
        super(effect);
    }

    @Override
    public KrrikSonOfYawgmothPhyrexianEffect copy() {
        return new KrrikSonOfYawgmothPhyrexianEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        FilterMana phyrexianBlack = new FilterMana();

        phyrexianBlack.setBlack(true);
        if (controller != null && sourcePermanent != null) {
            controller.addPhyrexianToColors(phyrexianBlack);
            return true;
        }
        return false;
    }
}
