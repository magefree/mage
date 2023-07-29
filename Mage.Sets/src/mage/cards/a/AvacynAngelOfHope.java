package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

/**
 * @author noxx
 */
public final class AvacynAngelOfHope extends CardImpl {

    public AvacynAngelOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flying, vigilance, indestructible
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(IndestructibleAbility.getInstance());

        // Other permanents you control have indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(IndestructibleAbility.getInstance(),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENTS, true)));
    }

    private AvacynAngelOfHope(final AvacynAngelOfHope card) {
        super(card);
    }

    @Override
    public AvacynAngelOfHope copy() {
        return new AvacynAngelOfHope(this);
    }
}
