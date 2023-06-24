package mage.game.permanent.token;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;

/**
 * @author TheElk801
 */
public final class TamiyosNotebookToken extends TokenImpl {

    private static final FilterCard filter = new FilterCard("spells");

    public TamiyosNotebookToken() {
        super("Tamiyo's Notebook", "Tamiyo's Notebook, a legendary colorless artifact token with \"Spells you cast cost {2} less to cast\" and \"{T}: Draw a card.\"");
        this.supertype.add(SuperType.LEGENDARY);
        this.cardType.add(CardType.ARTIFACT);
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 2)));
        this.addAbility(new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost()));
    }

    public TamiyosNotebookToken(final TamiyosNotebookToken token) {
        super(token);
    }

    public TamiyosNotebookToken copy() {
        return new TamiyosNotebookToken(this);
    }
}
