package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DeemInferior extends CardImpl {

    public DeemInferior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // This spell costs {1} less to cast for each card you've drawn this turn.
        Ability ability = new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionSourceEffect(CardsDrawnThisTurnDynamicValue.instance)
                        .setText("this spell costs {1} less to cast for each card you've drawn this turn")
        );
        this.addAbility(ability.addHint(CardsDrawnThisTurnDynamicValue.getHint()));

        // The owner of target nonland permanent puts it into their library second from the top or on the bottom.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(2, true));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private DeemInferior(final DeemInferior card) {
        super(card);
    }

    @Override
    public DeemInferior copy() {
        return new DeemInferior(this);
    }
}
