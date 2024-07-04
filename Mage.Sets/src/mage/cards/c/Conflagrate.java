package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class Conflagrate extends CardImpl {

    public Conflagrate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{R}");

        // Conflagrate deals X damage divided as you choose among any number of targets.
        this.getSpellAbility().addEffect(new DamageMultiEffect(GetXValue.instance));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(GetXValue.instance));

        // Flashback-{R}{R}, Discard X cards.
        Ability ability = new FlashbackAbility(this, new ManaCostsImpl<>("{R}{R}"));
        ability.addCost(new DiscardXTargetCost(StaticFilters.FILTER_CARD_CARDS));
        this.addAbility(ability);

    }

    private Conflagrate(final Conflagrate card) {
        super(card);
    }

    @Override
    public Conflagrate copy() {
        return new Conflagrate(this);
    }
}
