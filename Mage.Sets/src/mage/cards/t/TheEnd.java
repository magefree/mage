package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.effects.common.ExileTargetAndSearchGraveyardHandLibraryEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class TheEnd extends CardImpl {

    public TheEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // This spell costs {2} less to cast if your life total is 5 or less.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionSourceEffect(2, FatefulHourCondition.instance)
                        .setCanWorksOnStackOnly(true)
                        .setText("This spell costs {2} less to cast if your life total is 5 or less.")
        ).setRuleAtTheTop(true));

        // Exile target creature or planeswalker. Search its controller's graveyard, hand, and library for any number of cards with the same name as that permanent and exile them. That player shuffles, then draws a card for each card exiled from their hand this way.
        this.getSpellAbility().addEffect(new ExileTargetAndSearchGraveyardHandLibraryEffect(true, "its controller's", "any number of cards with the same name as that permanent", true));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private TheEnd(final TheEnd card) {
        super(card);
    }

    @Override
    public TheEnd copy() {
        return new TheEnd(this);
    }
}
