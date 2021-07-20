package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.AttacksIfAbleSourceEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronGolem extends CardImpl {

    public IronGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Iron Golem attacks or blocks each combat if able.
        Ability ability = new SimpleStaticAbility(new AttacksIfAbleSourceEffect(Duration.WhileOnBattlefield).setText("{this} attacks"));
        ability.addEffect(new BlocksIfAbleSourceEffect(Duration.WhileOnBattlefield).setText("blocks each combat if able").concatBy("or"));
        this.addAbility(ability);
    }

    private IronGolem(final IronGolem card) {
        super(card);
    }

    @Override
    public IronGolem copy() {
        return new IronGolem(this);
    }
}
