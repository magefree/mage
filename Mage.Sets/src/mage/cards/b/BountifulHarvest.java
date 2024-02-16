package mage.cards.b;

import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class BountifulHarvest extends CardImpl {

    public BountifulHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // You gain 1 life for each land you control.
        this.getSpellAbility().addEffect(new GainLifeEffect(LandsYouControlCount.instance)
                .setText("you gain 1 life for each land you control"));
        this.getSpellAbility().addHint(LandsYouControlHint.instance);
    }

    private BountifulHarvest(final BountifulHarvest card) {
        super(card);
    }

    @Override
    public BountifulHarvest copy() {
        return new BountifulHarvest(this);
    }
}
