package mage.cards.a;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchonOfAbsolution extends CardImpl {

    public ArchonOfAbsolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ARCHON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));

        // Creatures can't attack you or a planeswalker you control unless their controller pays {1} for each of those creatures.
        this.addAbility(new SimpleStaticAbility(new CantAttackYouUnlessPayAllEffect(
            Duration.WhileOnBattlefield,
            new ManaCostsImpl<>("{1}"),
            CantAttackYouUnlessPayAllEffect.Scope.YOU_AND_CONTROLLED_PLANESWALKERS
        )));
    }

    private ArchonOfAbsolution(final ArchonOfAbsolution card) {
        super(card);
    }

    @Override
    public ArchonOfAbsolution copy() {
        return new ArchonOfAbsolution(this);
    }
}
