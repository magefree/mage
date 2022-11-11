package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Wehk
 */
public final class RummagingWizard extends CardImpl {

    public RummagingWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{U}: Look at the top card of your library. You may put that card into your graveyard.
        this.addAbility(new SimpleActivatedAbility(new SurveilEffect(1), new ManaCostsImpl<>("{2}{U}")));
    }

    private RummagingWizard(final RummagingWizard card) {
        super(card);
    }

    @Override
    public RummagingWizard copy() {
        return new RummagingWizard(this);
    }
}
