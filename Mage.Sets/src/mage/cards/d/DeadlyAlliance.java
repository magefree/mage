package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeadlyAlliance extends CardImpl {

    public DeadlyAlliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // This spell costs {1} less to cast for each creature in your party.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, PartyCount.instance)
        ).addHint(PartyCountHint.instance).setRuleAtTheTop(true));

        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private DeadlyAlliance(final DeadlyAlliance card) {
        super(card);
    }

    @Override
    public DeadlyAlliance copy() {
        return new DeadlyAlliance(this);
    }
}
