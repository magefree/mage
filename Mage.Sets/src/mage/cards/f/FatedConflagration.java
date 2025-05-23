package mage.cards.f;

import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FatedConflagration extends CardImpl {

    public FatedConflagration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}{R}");


        // Fated Conflagration deals 5 damage to target creature or planewalker. If it's your turn, scry 2.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new ScryEffect(2, false), MyTurnCondition.instance, "If it's your turn, scry 2"));
        this.getSpellAbility().addHint(MyTurnHint.instance);
    }

    private FatedConflagration(final FatedConflagration card) {
        super(card);
    }

    @Override
    public FatedConflagration copy() {
        return new FatedConflagration(this);
    }
}
