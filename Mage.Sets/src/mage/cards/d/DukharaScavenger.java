
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class DukharaScavenger extends CardImpl {

    public DukharaScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // When Dukhara Scavenger enters the battlefield, you may put target artifact or creature card from your graveyard on top of your library.
        Effect effect = new PutOnLibraryTargetEffect(true);
        effect.setText("you may put target artifact or creature card from your graveyard on top of your library");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, true);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);
    }

    private DukharaScavenger(final DukharaScavenger card) {
        super(card);
    }

    @Override
    public DukharaScavenger copy() {
        return new DukharaScavenger(this);
    }
}
