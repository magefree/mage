

package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;


/**
 *
 * @author Loki
 */
public final class VedalkenCertarch extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or land");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public VedalkenCertarch (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // <i>Metalcraft</i> &mdash; {T}: Tap target artifact, creature, or land. Activate this ability only if you control three or more artifacts.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost(), MetalcraftCondition.instance);
        ability.setAbilityWord(AbilityWord.METALCRAFT);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public VedalkenCertarch (final VedalkenCertarch card) {
        super(card);
    }

    @Override
    public VedalkenCertarch copy() {
        return new VedalkenCertarch(this);
    }

}
