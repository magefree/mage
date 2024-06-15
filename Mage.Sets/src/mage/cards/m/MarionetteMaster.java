
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FabricateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MarionetteMaster extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("an artifact you control");

    public MarionetteMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Fabricate 3
        this.addAbility(new FabricateAbility(3));

        // Whenever an artifact you control is put into a graveyard from the battlefield, target opponent loses life equal to Marionette Master's power.
        Ability ability = new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new LoseLifeTargetEffect(new SourcePermanentPowerCount(false))
                        .setText("target opponent loses life equal to {this}'s power"),
                false, filter, false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private MarionetteMaster(final MarionetteMaster card) {
        super(card);
    }

    @Override
    public MarionetteMaster copy() {
        return new MarionetteMaster(this);
    }
}
