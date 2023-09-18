package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarteyeWitch extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public WarteyeWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Warteye Witch or another creature you control dies, scry 1.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new ScryEffect(1, false), false, filter
        ));
    }

    private WarteyeWitch(final WarteyeWitch card) {
        super(card);
    }

    @Override
    public WarteyeWitch copy() {
        return new WarteyeWitch(this);
    }
}
