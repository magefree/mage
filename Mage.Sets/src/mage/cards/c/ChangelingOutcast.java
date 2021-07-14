package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChangelingOutcast extends CardImpl {

    public ChangelingOutcast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Changeling Outcast can't block and can't be blocked.
        Ability ability = new CantBlockAbility();
        ability.addEffect(new CantBeBlockedSourceEffect().setText("and can't be blocked"));
        this.addAbility(ability);
    }

    private ChangelingOutcast(final ChangelingOutcast card) {
        super(card);
    }

    @Override
    public ChangelingOutcast copy() {
        return new ChangelingOutcast(this);
    }
}
