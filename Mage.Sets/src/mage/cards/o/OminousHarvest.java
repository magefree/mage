package mage.cards.o;

import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.GravestormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OminousHarvest extends CardImpl {

    public OminousHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Gravestorm
        this.addAbility(new GravestormAbility());

        // Target player draws a card and loses 1 life.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(1));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(1).withTargetDescription("and"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private OminousHarvest(final OminousHarvest card) {
        super(card);
    }

    @Override
    public OminousHarvest copy() {
        return new OminousHarvest(this);
    }
}
