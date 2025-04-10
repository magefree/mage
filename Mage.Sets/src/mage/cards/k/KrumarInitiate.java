package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.keyword.EndureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KrumarInitiate extends CardImpl {

    public KrumarInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {X}{B}, {T}, Pay X life: This creature endures X. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new EndureSourceEffect(GetXValue.instance, "{this}"), new ManaCostsImpl<>("{X}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(GetXValue.instance, "X life"));
        this.addAbility(ability);
    }

    private KrumarInitiate(final KrumarInitiate card) {
        super(card);
    }

    @Override
    public KrumarInitiate copy() {
        return new KrumarInitiate(this);
    }
}
