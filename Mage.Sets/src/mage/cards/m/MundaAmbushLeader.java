package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;

/**
 *
 * @author awjackson
 */
public final class MundaAmbushLeader extends CardImpl {

    private static final FilterCard filter = new FilterCard("Ally cards");

    static {
        filter.add(SubType.ALLY.getPredicate());
    }

    public MundaAmbushLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // <i>Rally</i>-Whenever Munda, Ambush Leader or another Ally enters the battlefield under your control,
        // you may look at the top four cards of your library. If you do, reveal any number of Ally cards from among them,
        // then put those cards on top of your library in any order and the rest on the bottom in any order.
        Effect effect = new LookLibraryAndPickControllerEffect(4, Integer.MAX_VALUE, filter, PutCards.TOP_ANY, PutCards.BOTTOM_ANY, false);
        effect.setText("look at the top four cards of your library. If you do, reveal any number of Ally cards from among them, "
                + "then put those cards on top of your library in any order and the rest on the bottom in any order");
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(effect, true));
    }

    private MundaAmbushLeader(final MundaAmbushLeader card) {
        super(card);
    }

    @Override
    public MundaAmbushLeader copy() {
        return new MundaAmbushLeader(this);
    }
}
