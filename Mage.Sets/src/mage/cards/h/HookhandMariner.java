package mage.cards.h;

import mage.MageInt;
import mage.abilities.keyword.DayboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HookhandMariner extends CardImpl {

    public HookhandMariner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.r.RiphookRaider.class;

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private HookhandMariner(final HookhandMariner card) {
        super(card);
    }

    @Override
    public HookhandMariner copy() {
        return new HookhandMariner(this);
    }
}
