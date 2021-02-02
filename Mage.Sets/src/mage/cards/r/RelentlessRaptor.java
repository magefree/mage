
package mage.cards.r;

import java.util.UUID;
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
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class RelentlessRaptor extends CardImpl {

    public RelentlessRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Relentless Raptor attacks or blocks each combat if able.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new AttacksIfAbleSourceEffect(Duration.WhileOnBattlefield, true).setText("{this} attacks"));
        ability.addEffect(new BlocksIfAbleSourceEffect(Duration.WhileOnBattlefield).setText("or blocks each combat if able"));
        this.addAbility(ability);
    }

    private RelentlessRaptor(final RelentlessRaptor card) {
        super(card);
    }

    @Override
    public RelentlessRaptor copy() {
        return new RelentlessRaptor(this);
    }
}
