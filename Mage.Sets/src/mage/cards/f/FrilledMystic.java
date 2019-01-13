package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrilledMystic extends CardImpl {

    public FrilledMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{U}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Frilled Mystic enters the battlefield, you may counter target spell.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CounterTargetEffect(), true);
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private FrilledMystic(final FrilledMystic card) {
        super(card);
    }

    @Override
    public FrilledMystic copy() {
        return new FrilledMystic(this);
    }
}
// nonagon infinity opens the door