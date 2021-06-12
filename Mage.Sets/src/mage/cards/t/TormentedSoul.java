package mage.cards.t;

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
 * @author Loki
 */
public final class TormentedSoul extends CardImpl {

    public TormentedSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        Ability ability = new CantBlockAbility();
        ability.addEffect(new CantBeBlockedSourceEffect().setText("and can't be blocked"));
        this.addAbility(ability);
    }

    private TormentedSoul(final TormentedSoul card) {
        super(card);
    }

    @Override
    public TormentedSoul copy() {
        return new TormentedSoul(this);
    }
}
