
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;


/**
 * @author noxx
 */
public final class AvacynAngelOfHope extends CardImpl {

    public AvacynAngelOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flying, vigilance, indestructible
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(IndestructibleAbility.getInstance());

        // Other permanents you control are indestructible.
        FilterControlledPermanent filter = new FilterControlledPermanent("Other permanents you control");
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true);
        effect.setText("Other permanents you control are indestructible");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private AvacynAngelOfHope(final AvacynAngelOfHope card) {
        super(card);
    }

    @Override
    public AvacynAngelOfHope copy() {
        return new AvacynAngelOfHope(this);
    }
}
