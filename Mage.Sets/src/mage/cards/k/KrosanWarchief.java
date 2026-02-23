package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class KrosanWarchief extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.BEAST, "Beast spells");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.BEAST);

    public KrosanWarchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Beast spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // {1}{G}: Regenerate target Beast.
        Ability ability = new SimpleActivatedAbility(new RegenerateTargetEffect(), new ManaCostsImpl<>("{1}{G}"));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private KrosanWarchief(final KrosanWarchief card) {
        super(card);
    }

    @Override
    public KrosanWarchief copy() {
        return new KrosanWarchief(this);
    }
}
