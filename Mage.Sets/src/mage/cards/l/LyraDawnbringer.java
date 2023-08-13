package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

public final class LyraDawnbringer extends CardImpl {

    private static final FilterCreaturePermanent AngelFilter = new FilterCreaturePermanent(SubType.ANGEL, "Angels");

    public LyraDawnbringer(UUID ownerID, CardSetInfo cardSetInfo) {
        super(ownerID, cardSetInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        subtype.add(SubType.ANGEL);
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(FlyingAbility.getInstance());
        addAbility(FirstStrikeAbility.getInstance());
        addAbility(LifelinkAbility.getInstance());

        // Other Angels you control get +1/+1 and have lifelink.
        Effect effect = new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, AngelFilter, true);
        effect.setText("Other Angels you control get +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        Effect effect2 = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, AngelFilter, true);
        effect2.setText("and have lifelink");
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    public LyraDawnbringer(final LyraDawnbringer lyraDawnbringer) {
        super(lyraDawnbringer);
    }

    public LyraDawnbringer copy() {
        return new LyraDawnbringer(this);
    }
}
