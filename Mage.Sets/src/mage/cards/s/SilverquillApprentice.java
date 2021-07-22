package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilverquillApprentice extends CardImpl {

    public SilverquillApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Magecraft — Whenever you cast or copy and instant or sorcery spell, target creature gets +1/+0 until end of turn.
        Ability ability = new MagecraftAbility(new BoostTargetEffect(1, 0, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SilverquillApprentice(final SilverquillApprentice card) {
        super(card);
    }

    @Override
    public SilverquillApprentice copy() {
        return new SilverquillApprentice(this);
    }
}
