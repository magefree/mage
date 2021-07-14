package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.*;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

/**
 *
 * @author weirddan455
 */
public final class AeveProgenitorOoze extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.OOZE);

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AeveProgenitorOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Storm
        this.addAbility(new StormAbility());

        // Aeve, Progenitor Ooze isn't legendary as long as it's a token.
        this.addAbility(new SimpleStaticAbility(new AeveProgenitorOozeNonLegendaryEffect()));

        // Aeve enters the battlefield with a +1/+1 counter on it for each other Ooze you control.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), new PermanentsOnBattlefieldCount(filter), true
        ), "with a +1/+1 counter on it for each other Ooze you control"
        ));
    }

    private AeveProgenitorOoze(final AeveProgenitorOoze card) {
        super(card);
    }

    @Override
    public AeveProgenitorOoze copy() {
        return new AeveProgenitorOoze(this);
    }
}

class AeveProgenitorOozeNonLegendaryEffect extends ContinuousEffectImpl {

    public AeveProgenitorOozeNonLegendaryEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.staticText = "{this} isn't legendary if it's a token";
    }

    private AeveProgenitorOozeNonLegendaryEffect(final AeveProgenitorOozeNonLegendaryEffect effect) {
        super(effect);
    }

    @Override
    public AeveProgenitorOozeNonLegendaryEffect copy() {
        return new AeveProgenitorOozeNonLegendaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent instanceof PermanentToken) {
            permanent.getSuperType().remove(SuperType.LEGENDARY);
            return true;
        }
        return false;
    }
}
