package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksCreatureTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class VertigoSpawn extends CardImpl {

    public VertigoSpawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever Vertigo Spawn blocks a creature, tap that creature. That creature doesn't untap during its controller's next untap step.
        Ability ability = new BlocksCreatureTriggeredAbility(new TapTargetEffect("tap that creature"));
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("that creature"));
        this.addAbility(ability);
    }

    private VertigoSpawn(final VertigoSpawn card) {
        super(card);
    }

    @Override
    public VertigoSpawn copy() {
        return new VertigoSpawn(this);
    }
}
