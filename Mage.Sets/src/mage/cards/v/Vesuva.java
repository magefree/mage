package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;

/**
 *
 * @author North
 */
public final class Vesuva extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("land");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public Vesuva(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // You may have Vesuva enter the battlefield tapped as a copy of any land on the battlefield.
        Effect effect = new TapSourceEffect(true);
        effect.setText("tapped");
        Ability ability = new EntersBattlefieldAbility(effect, true);
        effect = new CopyPermanentEffect(filter);
        effect.setText(" as a copy of any land on the battlefield");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private Vesuva(final Vesuva card) {
        super(card);
    }

    @Override
    public Vesuva copy() {
        return new Vesuva(this);
    }
}
