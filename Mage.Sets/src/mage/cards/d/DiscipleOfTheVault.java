
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public final class DiscipleOfTheVault extends CardImpl {

    public DiscipleOfTheVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an artifact is put into a graveyard from the battlefield, you may have target opponent lose 1 life.
        Effect effect = new LoseLifeTargetEffect(1);
        effect.setText("you may have target opponent lose 1 life");
        Ability ability = new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.GRAVEYARD,
                effect, new FilterArtifactPermanent(),
                "Whenever an artifact is put into a graveyard from the battlefield, ", true);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private DiscipleOfTheVault(final DiscipleOfTheVault card) {
        super(card);
    }

    @Override
    public DiscipleOfTheVault copy() {
        return new DiscipleOfTheVault(this);
    }
}
