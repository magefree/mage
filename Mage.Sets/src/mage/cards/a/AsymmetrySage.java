package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AsymmetrySage extends CardImpl {

    public AsymmetrySage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, target creature you control has base power 3 until end of turn.
        Ability ability = new MagecraftAbility(new SetBasePowerToughnessTargetEffect(
                StaticValue.get(3), null, Duration.EndOfTurn
        ).setText("target creature you control has base power 3 until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private AsymmetrySage(final AsymmetrySage card) {
        super(card);
    }

    @Override
    public AsymmetrySage copy() {
        return new AsymmetrySage(this);
    }
}
