package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpoilsOfAdventure extends CardImpl {

    public SpoilsOfAdventure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}{U}");

        // This spell costs {1} less to cast for each creature in your party.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, PartyCount.instance)
        ).addHint(PartyCountHint.instance).setRuleAtTheTop(true));

        // You gain 3 life and draw three cards.
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3).concatBy("and"));
    }

    private SpoilsOfAdventure(final SpoilsOfAdventure card) {
        super(card);
    }

    @Override
    public SpoilsOfAdventure copy() {
        return new SpoilsOfAdventure(this);
    }
}
