package mage.cards.e;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class EssenceHarvest extends CardImpl {

    public EssenceHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player loses X life and you gain X life, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(
                new LoseLifeTargetEffect(GreatestAmongPermanentsValue.Instanced.PowerControlledCreatures)
                        .setText("target player loses X life")
        );
        this.getSpellAbility().addEffect(
                new GainLifeEffect(GreatestAmongPermanentsValue.Instanced.PowerControlledCreatures)
                        .setText("and you gain X life, where X is the greatest power among creatures you control")
        );
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.Instanced.PowerControlledCreatures.getHint());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private EssenceHarvest(final EssenceHarvest card) {
        super(card);
    }

    @Override
    public EssenceHarvest copy() {
        return new EssenceHarvest(this);
    }
}