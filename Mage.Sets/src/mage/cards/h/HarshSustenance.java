package mage.cards.h;

import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HarshSustenance extends CardImpl {

    public HarshSustenance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{B}");

        // Harsh Sustenance deals X damage to any target and you gain X life, where X is the number of creatures you control.
        Effect effect = new DamageTargetEffect(CreaturesYouControlCount.instance);
        effect.setText("{this} deals X damage to any target");
        getSpellAbility().addEffect(effect);
        getSpellAbility().addTarget(new TargetAnyTarget());
        effect = new GainLifeEffect(CreaturesYouControlCount.instance);
        effect.setText("and you gain X life, where X is the number of creatures you control");
        getSpellAbility().addEffect(effect);
        getSpellAbility().addHint(CreaturesYouControlHint.instance);
    }

    private HarshSustenance(final HarshSustenance card) {
        super(card);
    }

    @Override
    public HarshSustenance copy() {
        return new HarshSustenance(this);
    }
}
