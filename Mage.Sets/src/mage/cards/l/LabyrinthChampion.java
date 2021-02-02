
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class LabyrinthChampion extends CardImpl {

    public LabyrinthChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Heroic - Whenever you cast a spell that targets Labyrinth Champion, Labyrinth Champion deals 2 damage to any target.
        Ability ability = new HeroicAbility(new DamageTargetEffect(2), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private LabyrinthChampion(final LabyrinthChampion card) {
        super(card);
    }

    @Override
    public LabyrinthChampion copy() {
        return new LabyrinthChampion(this);
    }
}
