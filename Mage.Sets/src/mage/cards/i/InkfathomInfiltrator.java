package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author North
 */
public final class InkfathomInfiltrator extends CardImpl {

    public InkfathomInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U/B}{U/B}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        Ability ability = new CantBlockAbility();
        ability.addEffect(new CantBeBlockedSourceEffect().setText("and can't be blocked"));
        this.addAbility(ability);
    }

    private InkfathomInfiltrator(final InkfathomInfiltrator card) {
        super(card);
    }

    @Override
    public InkfathomInfiltrator copy() {
        return new InkfathomInfiltrator(this);
    }
}
