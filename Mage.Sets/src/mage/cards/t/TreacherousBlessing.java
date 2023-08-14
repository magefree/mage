package mage.cards.t;

import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreacherousBlessing extends CardImpl {

    public TreacherousBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // When Treacherous Blessing enters the battlefield, draw three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(3)));

        // Whenever you cast a spell, you lose 1 life.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new LoseLifeSourceControllerEffect(1), false
        ));

        // When Treacherous Blessing becomes the target of a spell or ability, sacrifice it.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(new SacrificeSourceEffect().setText("sacrifice it")));
    }

    private TreacherousBlessing(final TreacherousBlessing card) {
        super(card);
    }

    @Override
    public TreacherousBlessing copy() {
        return new TreacherousBlessing(this);
    }
}
