package mage.cards.a;

import mage.abilities.condition.common.RaidCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.TargetSpell;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author L_J
 */
public final class AdmiralsOrder extends CardImpl {

    public AdmiralsOrder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Raid - If you attacked with a creature this turn, you may pay {U} rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{U}"), RaidCondition.instance,
                        "<br/><i>Raid</i> &mdash; If you attacked this turn, you may pay {U} rather than pay this spell's mana cost"),
                new PlayerAttackedWatcher());
        // Counter target spell. 
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addHint(RaidHint.instance);
    }

    private AdmiralsOrder(final AdmiralsOrder card) {
        super(card);
    }

    @Override
    public AdmiralsOrder copy() {
        return new AdmiralsOrder(this);
    }
}
