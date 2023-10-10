
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryGraveOfSourceOwnerEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author Loki
 */
public final class KozilekButcherOfTruth extends CardImpl {

    public KozilekButcherOfTruth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{10}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // When you cast Kozilek, Butcher of Truth, draw four cards.
        this.addAbility(new CastSourceTriggeredAbility(new DrawCardSourceControllerEffect(4)));

        // Annihilator 4 (Whenever this creature attacks, defending player sacrifices four permanents.)
        this.addAbility(new AnnihilatorAbility(4));

        // When Kozilek is put into a graveyard from anywhere, its owner shuffles their graveyard into their library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibraryGraveOfSourceOwnerEffect(), false));
    }

    private KozilekButcherOfTruth(final KozilekButcherOfTruth card) {
        super(card);
    }

    @Override
    public KozilekButcherOfTruth copy() {
        return new KozilekButcherOfTruth(this);
    }

}
